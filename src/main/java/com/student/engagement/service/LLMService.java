package com.student.engagement.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LLMService {

    private final List<Map<String, Object>> universityKnowledgeBase = new ArrayList<>();

    public LLMService() {
        initializeKnowledgeBase();
        System.out.println("? LLMService initialized with " + universityKnowledgeBase.size() + " universities");
    }

    private void initializeKnowledgeBase() {
        // USA Universities
        addUniversity("Stanford University", "USA", 5, "Computer Science, AI, ML, Data Science", 150000, 55000, 
                     "silicon valley california ai ml tech", "Merit + Need based", "Medium");
        
        addUniversity("MIT", "USA", 1, "Engineering, AI, Robotics, CS", 145000, 58000, 
                     "boston engineering robotics ai", "Full scholarships available", "Medium");
        
        addUniversity("Harvard University", "USA", 3, "CS, Data Science, Business", 140000, 56000, 
                     "boston ivy league research", "Need-based generous", "Medium");
        
        addUniversity("Carnegie Mellon University", "USA", 25, "AI, Robotics, HCI, CS", 140000, 52000, 
                     "pittsburgh ai robotics", "Merit-based", "Medium");
        
        addUniversity("UC Berkeley", "USA", 10, "CS, Data Science, AI", 148000, 54000, 
                     "california berkeley ai data", "Research assistantships", "Medium");
        
        // Canada Universities
        addUniversity("University of Toronto", "Canada", 18, "CS, AI, Data Science, Engineering", 85000, 50000, 
                     "toronto ontario ai research", "Vanier, OGS scholarships", "Easy");
        
        addUniversity("University of British Columbia", "Canada", 34, "CS, Data Science, Business", 80000, 48000, 
                     "vancouver bc ai data", "International Major Entrance Scholarship", "Easy");
        
        addUniversity("McGill University", "Canada", 31, "CS, AI, Engineering", 78000, 45000, 
                     "montreal quebec ai french", "Merit scholarships", "Easy");
        
        // Germany Universities
        addUniversity("Technical University of Munich", "Germany", 28, "Engineering, CS, AI, Automotive", 65000, 3000, 
                     "munich bavaria engineering low tuition", "DAAD scholarships", "Easy");
        
        addUniversity("RWTH Aachen University", "Germany", 45, "Engineering, CS, Mechanical", 60000, 3000, 
                     "aachen north rhine engineering", "RWTH International Academy", "Easy");
        
        addUniversity("University of Stuttgart", "Germany", 120, "AI, Engineering, CS", 58000, 3000, 
                     "stuttgart baden-wurttemberg automotive ai", "Scholarships available", "Easy");
        
        // Singapore Universities
        addUniversity("National University of Singapore", "Singapore", 11, "CS, AI, Data Science, Business", 80000, 35000, 
                     "singapore asia tech research", "ASEAN scholarships", "Medium");
        
        addUniversity("Nanyang Technological University", "Singapore", 26, "Engineering, CS, AI", 78000, 34000, 
                     "singapore engineering tech", "NTU scholarships", "Medium");
        
        // UK Universities
        addUniversity("University of Oxford", "UK", 4, "CS, AI, Data Science, Research", 120000, 50000, 
                     "oxford research liberal arts", "Rhodes scholarships", "Medium");
        
        addUniversity("University of Cambridge", "UK", 2, "CS, Engineering, Research", 118000, 52000, 
                     "cambridge research engineering", "Gates Cambridge", "Medium");
        
        addUniversity("Imperial College London", "UK", 6, "Engineering, CS, AI, Data Science", 110000, 48000, 
                     "london engineering tech ai", "President's scholarships", "Medium");
        
        // Australia Universities
        addUniversity("University of Melbourne", "Australia", 33, "CS, Data Science, AI", 75000, 45000, 
                     "melbourne victoria research", "Melbourne scholarships", "Medium");
    }

    private void addUniversity(String name, String country, int ranking, String courses, 
                                double avgSalary, double cost, String keywords, 
                                String scholarships, String visaDifficulty) {
        Map<String, Object> uni = new HashMap<>();
        uni.put("name", name);
        uni.put("country", country);
        uni.put("ranking", ranking);
        uni.put("courses", courses);
        uni.put("avgSalary", avgSalary);
        uni.put("cost", cost);
        uni.put("keywords", keywords.toLowerCase());
        uni.put("scholarships", scholarships);
        uni.put("visaDifficulty", visaDifficulty);
        universityKnowledgeBase.add(uni);
    }

    public List<Map<String, Object>> retrieveRelevantUniversities(String query, int topK) {
        if (query == null || query.isEmpty() || query.equals("all")) {
            return universityKnowledgeBase.stream().limit(topK).collect(Collectors.toList());
        }
        
        String queryLower = query.toLowerCase();
        List<Map<String, Object>> scored = new ArrayList<>();
        
        for (Map<String, Object> uni : universityKnowledgeBase) {
            double score = calculateRelevanceScore(uni, queryLower);
            if (score > 10) {
                Map<String, Object> result = new HashMap<>(uni);
                result.put("relevanceScore", Math.min(100, (int) score));
                scored.add(result);
            }
        }
        
        scored.sort((a, b) -> Double.compare((double) b.get("relevanceScore"), (double) a.get("relevanceScore")));
        return scored.stream().limit(topK).collect(Collectors.toList());
    }

    private double calculateRelevanceScore(Map<String, Object> uni, String query) {
        double score = 30.0;
        
        String keywords = ((String) uni.get("keywords")).toLowerCase();
        String name = ((String) uni.get("name")).toLowerCase();
        String country = ((String) uni.get("country")).toLowerCase();
        String courses = ((String) uni.get("courses")).toLowerCase();
        
        String[] queryTerms = query.split("\\s+");
        for (String term : queryTerms) {
            if (term.length() < 2) continue;
            if (keywords.contains(term)) score += 15;
            if (name.contains(term)) score += 12;
            if (country.contains(term)) score += 10;
            if (courses.contains(term)) score += 8;
        }
        
        if (query.contains("low tuition") || query.contains("cheap") || query.contains("budget")) {
            double cost = (double) uni.get("cost");
            if (cost < 10000) score += 25;
            else if (cost < 30000) score += 10;
        }
        
        if (query.contains("high salary") || query.contains("roi")) {
            double salary = (double) uni.get("avgSalary");
            if (salary > 120000) score += 20;
            else if (salary > 90000) score += 10;
        }
        
        int ranking = (int) uni.get("ranking");
        if (ranking <= 10) score += 20;
        else if (ranking <= 30) score += 15;
        else if (ranking <= 50) score += 10;
        
        return score;
    }

    public String generateCareerAdvice(String studentProfile, String universityOptions) {
        return String.format("""
            ?? Personalized Career Advice for %s
            
            ?? Top University Recommendations:
            ? Stanford University - Best for AI/ML (Match: 94%%)
              ? Silicon Valley connections, $150k avg salary
            ? University of Toronto - Great for research (Match: 89%%)
              ? 3 year work permit, easier PR
            ? TU Munich - Budget-friendly (Match: 87%%)
              ? ?3k tuition, strong engineering
            
            ?? Cost Analysis:
            ? USA: ?50-60 Lakhs/year (Highest ROI)
            ? Canada: ?35-40 Lakhs/year (Best work-life balance)
            ? Germany: ?8-12 Lakhs/year (Most affordable)
            
            ?? Action Plan (Next 6 months):
            1. Prepare for IELTS/TOEFL (Target: 7.5+)
            2. Build portfolio with 3-4 projects
            3. Apply for scholarships 6-8 months in advance
            4. Network with alumni on LinkedIn
            
            ?? Risk Factors:
            ? USA: H1B visa uncertainty
            ? Canada: Higher taxes, cold climate
            ? Germany: Language barrier for non-tech roles
            """, studentProfile);
    }

    public String generateLoanAdvice(double loanAmount, int tenure, double expectedSalary) {
        double emi = calculateEMI(loanAmount, 11.0, tenure);
        double monthlyIncome = expectedSalary / 12;
        double emiRatio = (emi / monthlyIncome) * 100;
        
        String affordability = emiRatio <= 40 ? "COMFORTABLE" : (emiRatio <= 60 ? "MANAGEABLE" : "STRETCHED");
        
        return String.format("""
            ?? LOAN ANALYSIS
            
            ?? Loan Details:
            ? Loan Amount: ?%.2f Lakhs
            ? Tenure: %d years
            ? Expected Salary: ?%.2f Lakhs/year
            ? Monthly EMI: ?%.2f
            ? EMI/Income Ratio: %.1f%%
            
            ?? Affordability: %s
            
            ?? Recommendations:
            %s
            
            ?? Next Steps:
            1. Apply for scholarships to reduce loan burden
            2. Consider part-time work during studies (20hrs/week)
            3. Look for universities with co-op programs
            """, loanAmount/100000, tenure, expectedSalary/100000, emi, emiRatio, affordability,
            emiRatio <= 40 ? "? Loan is affordable. Proceed confidently." :
            emiRatio <= 60 ? "?? Loan is manageable. Consider reducing expenses." :
            "? Loan may be risky. Look for cheaper alternatives.");
    }
    
    private double calculateEMI(double principal, double rate, int years) {
        double monthlyRate = rate / 12 / 100;
        int months = years * 12;
        return principal * monthlyRate * Math.pow(1 + monthlyRate, months) / 
               (Math.pow(1 + monthlyRate, months) - 1);
    }
}
