# 🎓 StudentEngage

> A smart FinTech platform built for students planning to study abroad — combining loan eligibility checks, university discovery, career guidance, and document tracking in one place.

---

## 📌 Overview

**StudentEngage** is a Java Spring Boot web application designed to simplify the study abroad journey for Indian students. It provides tools to assess loan eligibility, explore universities, get career advice, and track application documents — all from a single dashboard.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🏦 **Loan Eligibility** | Check eligibility for education loans based on academic profile |
| 🌍 **University Explorer** | Browse and shortlist universities for abroad applications |
| 💼 **Career Advice** | Get guidance on career paths and roles aligned with your profile |
| 📋 **Document Tracker** | Track all study abroad documents with progress visualization |
| 📊 **Dashboard** | Unified view of your application journey |

---

## 🛠️ Tech Stack

- **Backend:** Java · Spring Boot
- **Frontend:** HTML · Tailwind CSS · Font Awesome
- **Build Tool:** Apache Maven
- **Server:** Embedded Tomcat (via Spring Boot)

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Apache Maven 3.6+

### Run Locally

```bash
# Clone the repository
git clone https://github.com/Dutta-Raj/StudentEngage.git
cd StudentEngage

# Build and run
mvn spring-boot:run
```

Then open your browser at:

```
http://localhost:8080
```

---

## 📁 Project Structure

```
StudentEngage/
├── src/
│   └── main/
│       ├── java/                    # Spring Boot application code
│       └── resources/
│           └── static/              # Frontend HTML pages
│               ├── index.html       # Main dashboard
│               └── tracker.html     # Document tracker
├── pom.xml                          # Maven dependencies
└── README.md
```

---

## 📋 Document Tracker

The `/tracker.html` page helps students manage their study abroad paperwork:

- ✅ Tracks 15 key documents across 5 categories (Personal, Academic, Tests, Financial, Visa)
- 📊 Live progress bar
- 💾 Download progress report as `.txt`
- 📋 One-click copy to clipboard
- 🔄 Reset functionality
- 💡 Persists state via `localStorage`

### Tracked Documents

**Personal** — Passport, Photos, Signature  
**Academic** — Transcripts, Degree Certificate, CGPA Certificate  
**Tests** — IELTS/TOEFL, GRE/GMAT  
**Financial** — Bank Statements, ITR, Loan Sanction Letter  
**Visa** — Offer Letter, I-20/CAS, Visa Fee Receipt, Medical Report

---

## 🔗 Pages

| URL | Page |
|---|---|
| `http://localhost:8080` | Main Dashboard |
| `http://localhost:8080/tracker.html` | Document Tracker |

---

## 👤 Author

**Rajdeep Dutta**  
[GitHub](https://github.com/Dutta-Raj) · KIIT University

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
