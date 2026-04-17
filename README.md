# 🎓 StudentEngage

> A smart AI-powered FinTech platform for Indian students planning to study abroad — combining RAG-based Q&A, loan eligibility checks, university discovery, career guidance, and document tracking in one place.

---

## 📌 Overview

**StudentEngage** is a Java Spring Boot web application that uses **Retrieval-Augmented Generation (RAG)** and **OpenAI GPT** to answer student queries intelligently. Instead of static responses, the AI retrieves relevant knowledge from a curated knowledge base and generates accurate, context-aware answers about loans, universities, careers, and visa documents.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🤖 **AI Chat (RAG + GPT)** | Ask anything — powered by OpenAI GPT-4o-mini + RAG |
| 🏦 **Loan Eligibility** | AI guidance on SBI, HDFC, Axis Bank education loans |
| 🌍 **University Explorer** | AI recommendations for USA, UK, Canada, Germany |
| 💼 **Career Advice** | AI-powered guidance for SDE, Data Science, and more |
| 📋 **Document Tracker** | Track all study abroad docs with live progress bar |
| 📊 **Dashboard** | Unified view of your entire application journey |

---

## 🧠 How RAG Works in This Project

```
User Question
      │
      ▼
 Embedding Model (text-embedding-3-small)
      │  converts question → vector
      ▼
 Vector Store (In-Memory SimpleVectorStore)
      │  finds top 4 most similar knowledge chunks
      ▼
 Context Retrieved (loans / universities / career / visa docs)
      │
      ▼
 GPT-4o-mini (ChatClient)
      │  system prompt = context + role instructions
      ▼
 Accurate, Grounded Answer → User
```

**Why RAG?** Without RAG, GPT might hallucinate loan amounts or give outdated visa info. RAG grounds every answer in your own curated knowledge base.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17, Spring Boot 3.3 |
| **AI / LLM** | OpenAI GPT-4o-mini via Spring AI |
| **RAG / Embeddings** | Spring AI + `text-embedding-3-small` + `SimpleVectorStore` |
| **Frontend** | HTML, Tailwind CSS, Font Awesome |
| **Build Tool** | Apache Maven 3.6+ |
| **Server** | Embedded Tomcat |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Apache Maven 3.6+
- OpenAI API key → [Get one here](https://platform.openai.com/api-keys)

### 1. Clone the repository
```bash
git clone https://github.com/Dutta-Raj/StudentEngage.git
cd StudentEngage
```

### 2. Set your OpenAI API key
Open `src/main/resources/application.properties` and add:
```properties
spring.ai.openai.api-key=YOUR_OPENAI_API_KEY_HERE
spring.ai.openai.chat.options.model=gpt-4o-mini
spring.ai.openai.embedding.options.model=text-embedding-3-small
```

### 3. Add Spring AI to `pom.xml`

Inside `<dependencyManagement>`:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-bom</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

Inside `<dependencies>`:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-core</artifactId>
</dependency>
```

### 4. Run
```bash
mvn spring-boot:run
```
Open: `http://localhost:8080`

---

## 🔌 AI API Endpoints

### General RAG Chat
```http
POST /api/ai/chat
{ "question": "What loans are available for studying in the USA?" }
```

### Category-Specific Chat
```http
POST /api/ai/chat/loan
POST /api/ai/chat/university
POST /api/ai/chat/career
POST /api/ai/chat/documents
```

### Health Check
```http
GET /api/ai/health
```

---

## 🧩 Project Structure

```
StudentEngage/
├── src/main/java/com/studentengage/
│   ├── controller/
│   │   └── AiController.java       # REST endpoints for AI chat
│   └── service/
│       └── AiService.java          # RAG logic + LLM calls
├── src/main/resources/
│   ├── static/
│   │   ├── index.html              # Main dashboard
│   │   └── tracker.html            # Document tracker
│   └── application.properties      # OpenAI config
├── pom.xml
└── README.md
```

---

## 📋 RAG Knowledge Base

| Category | Topics Covered |
|---|---|
| 🏦 Loans | SBI, HDFC Credila, Axis Bank — amounts, rates, eligibility |
| 🌍 Universities | USA, UK, Canada, Germany — top schools, fees, requirements |
| 💼 Career | SDE prep, Data Science, fresher strategies |
| 📄 Documents | US F-1 visa, UK Tier 4 visa, general study abroad docs |

> **Extend it:** Add more `Document` objects in `AiService.java` → auto-indexed on startup.

---

## 💡 Example Questions

- *"How much loan can I get for studying in Canada?"*
- *"What documents do I need for a US F-1 visa?"*
- *"Is Germany free for international students?"*
- *"How should a fresher prepare for Google SDE?"*

---

## 👤 Author

**Rajdeep Dutta** · [GitHub](https://github.com/Dutta-Raj) · KIIT University

---

## 📄 License

MIT License
