package com.student.engagement.controller;

import com.student.engagement.service.LLMService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EngagementController {

    private final LLMService llmService;

    public EngagementController(LLMService llmService) {
        this.llmService = llmService;
    }
    
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "online");
        response.put("service", "Student Engagement Platform");
        response.put("version", "2.0.0");
        response.put("message", "AI-powered career guidance system is running!");
        return response;
    }
    
    @PostMapping("/career/advice")
    public Map<String, Object> getCareerAdvice(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new LinkedHashMap<>();
        
        try {
            String name = request.getOrDefault("name", "Student");
            String cgpa = request.getOrDefault("cgpa", "8.5");
            String field = request.getOrDefault("field", "Computer Science");
            String budget = request.getOrDefault("budget", "50");
            
            String studentProfile = String.format("Name: %s, CGPA: %s, Field: %s, Budget: ?%s Lakhs", 
                                                  name, cgpa, field, budget);
            
            String query = field + " " + budget + " budget";
            List<Map<String, Object>> relevantUnis = llmService.retrieveRelevantUniversities(query, 5);
            
            StringBuilder uniOptions = new StringBuilder();
            for (Map<String, Object> uni : relevantUnis) {
                uniOptions.append(String.format("- %s (%s): Rank #%d\n", 
                    uni.get("name"), uni.get("country"), uni.get("ranking")));
            }
            
            String aiAdvice = llmService.generateCareerAdvice(studentProfile, uniOptions.toString());
            
            response.put("status", "success");
            response.put("student_name", name);
            response.put("ai_powered_advice", aiAdvice);
            response.put("rag_retrieved_universities", relevantUnis);
            response.put("confidence_score", 0.92);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("ai_powered_advice", getFallbackAdvice());
            response.put("rag_retrieved_universities", getFallbackUniversities());
            response.put("error", e.getMessage());
        }
        
        return response;
    }
    
    @PostMapping("/loan/eligibility")
    public Map<String, Object> checkLoanEligibility(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new LinkedHashMap<>();
        
        try {
            double loanAmount = ((Number) request.getOrDefault("loanAmount", 4000000)).doubleValue();
            int tenure = ((Number) request.getOrDefault("tenure", 10)).intValue();
            double expectedSalary = ((Number) request.getOrDefault("expectedSalary", 1200000)).doubleValue();
            
            double monthlyIncome = expectedSalary / 12;
            double emi = calculateEMI(loanAmount, 11.0, tenure);
            double emiRatio = (emi / monthlyIncome) * 100;
            
            String status = emiRatio <= 40 ? "ELIGIBLE" : (emiRatio <= 60 ? "PARTIALLY_ELIGIBLE" : "HIGH_RISK");
            String aiLoanAdvice = llmService.generateLoanAdvice(loanAmount, tenure, expectedSalary);
            
            response.put("status", status);
            response.put("loan_amount_lakhs", loanAmount / 100000);
            response.put("tenure_years", tenure);
            response.put("monthly_emi", String.format("?%.2f", emi));
            response.put("emi_to_income_ratio", String.format("%.1f%%", emiRatio));
            response.put("ai_loan_advice", aiLoanAdvice);
            response.put("recommendation", emiRatio <= 40 ? "Loan is affordable" : 
                                           (emiRatio <= 60 ? "Loan is manageable" : "Consider reducing loan amount"));
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("ai_loan_advice", "Unable to analyze loan at this moment. Please try again.");
            response.put("error", e.getMessage());
        }
        
        return response;
    }
    
    private double calculateEMI(double principal, double rate, int years) {
        double monthlyRate = rate / 12 / 100;
        int months = years * 12;
        return principal * monthlyRate * Math.pow(1 + monthlyRate, months) / 
               (Math.pow(1 + monthlyRate, months) - 1);
    }
    
    @GetMapping("/universities")
    public Map<String, Object> getUniversities(@RequestParam(required = false) String query) {
        Map<String, Object> response = new LinkedHashMap<>();
        
        try {
            List<Map<String, Object>> universities;
            if (query != null && !query.isEmpty()) {
                universities = llmService.retrieveRelevantUniversities(query, 10);
            } else {
                universities = llmService.retrieveRelevantUniversities("all", 10);
            }
            
            response.put("universities", universities);
            response.put("count", universities.size());
            response.put("retrieval_method", "RAG (Retrieval Augmented Generation)");
            
        } catch (Exception e) {
            response.put("universities", getFallbackUniversities());
            response.put("count", 8);
            response.put("error", e.getMessage());
        }
        
        return response;
    }
    
    private String getFallbackAdvice() {
        return """
        ?? Personalized Career Advice:
        
        Based on your profile, here are key recommendations:
        
        ?? Top University Recommendations:
        ? Stanford University - Best for AI/ML specialization
          ? Strong industry connections in Silicon Valley
          ? Average starting salary: $150,000/year
        
        ? University of Toronto - Excellent research opportunities
          ? Post-study work permit up to 3 years
          ? Easier PR pathway compared to USA
        
        ? TU Munich - Most budget-friendly option
          ? Low tuition fees (?3,000/year)
          ? Strong engineering reputation in Europe
        
        ?? Cost Breakdown:
        ? USA: ?50-60 Lakhs/year (Tuition + Living)
        ? Canada: ?35-40 Lakhs/year
        ? Germany: ?8-12 Lakhs/year
        
        ?? Action Items (Next 6 months):
        1. Prepare for IELTS/TOEFL (Target: 7.5+)
        2. Build GitHub portfolio with 3-4 ML projects
        3. Apply for scholarships (DAAD, Vanier, etc.)
        4. Network with alumni on LinkedIn
        
        ?? Risk Factors to Consider:
        ? USA: H1B visa lottery uncertainty
        ? Canada: Harsh winters, higher taxes
        ? Germany: Language barrier for non-tech roles
        """;
    }
    
    private List<Map<String, Object>> getFallbackUniversities() {
        List<Map<String, Object>> universities = new ArrayList<>();
        
        Map<String, Object> uni1 = new HashMap<>();
        uni1.put("name", "Stanford University");
        uni1.put("country", "USA");
        uni1.put("ranking", 5);
        uni1.put("courses", "AI, ML, Data Science");
        uni1.put("avgSalary", 150000);
        uni1.put("cost", 55000);
        uni1.put("relevanceScore", 94);
        universities.add(uni1);
        
        Map<String, Object> uni2 = new HashMap<>();
        uni2.put("name", "MIT");
        uni2.put("country", "USA");
        uni2.put("ranking", 1);
        uni2.put("courses", "Engineering, AI, Robotics");
        uni2.put("avgSalary", 145000);
        uni2.put("cost", 58000);
        uni2.put("relevanceScore", 92);
        universities.add(uni2);
        
        Map<String, Object> uni3 = new HashMap<>();
        uni3.put("name", "University of Toronto");
        uni3.put("country", "Canada");
        uni3.put("ranking", 18);
        uni3.put("courses", "CS, AI, Data Science");
        uni3.put("avgSalary", 85000);
        uni3.put("cost", 50000);
        uni3.put("relevanceScore", 88);
        universities.add(uni3);
        
        Map<String, Object> uni4 = new HashMap<>();
        uni4.put("name", "TU Munich");
        uni4.put("country", "Germany");
        uni4.put("ranking", 28);
        uni4.put("courses", "Engineering, CS, AI");
        uni4.put("avgSalary", 65000);
        uni4.put("cost", 3000);
        uni4.put("relevanceScore", 86);
        universities.add(uni4);
        
        return universities;
    }
}
