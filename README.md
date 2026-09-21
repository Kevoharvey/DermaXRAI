# 🧴 SkinAI

> An AI-powered skincare application that uses Deep Learning to analyze skin images and identify potential skin conditions.

## 📌 About the Project

**SkinAI** is an Android application project focused on analyzing skin images and providing users with information about potential skin conditions.

The project combines:

* 🧠 **Deep Learning** for skin-condition image classification
* 📱 **Android** for the mobile application
* 📸 **Image Analysis** for user-provided skin images
* 🎨 **UI/UX Design** using Google Stitch and Figma
* 📊 **Machine Learning Evaluation** to measure model performance

> ⚠️ **Disclaimer:** SkinAI is an educational/research project and is not intended to provide a definitive medical diagnosis. Model predictions should not replace professional medical advice.

---

## 🎯 Project Goals

The main goals of SkinAI are to:

* 🧠 Develop a Deep Learning model capable of classifying selected skin conditions.
* 📸 Allow users to provide skin images through the Android application.
* 🔬 Evaluate the model using appropriate performance metrics.
* 📱 Integrate the trained model into the Android application.
* 🎨 Create a clear and accessible mobile interface.
* 🚀 Build an end-to-end AI application from dataset collection to mobile deployment.

---

## 🏗️ Project Development

The project will be developed through several stages:

```text
📊 Dataset
    ↓
🧹 Data Preparation
    ↓
🧠 Deep Learning Model
    ↓
📈 Model Evaluation
    ↓
📦 Model Optimization
    ↓
📱 Android Integration
    ↓
📸 Image Input
    ↓
🔍 Skin Analysis
    ↓
📋 Results
```

---

## 🧠 Deep Learning

The Deep Learning component will be responsible for analyzing skin images and predicting the supported skin-condition classes.

The model-development process will include:

* 📊 Dataset research and collection
* 🧹 Data cleaning
* 🔄 Data preprocessing
* 🖼️ Data augmentation
* 🧠 Model development
* 🏋️ Model training
* 📈 Evaluation
* ⚙️ Optimization
* 📦 Mobile model conversion
* 📱 Android inference

The final supported conditions will depend on the quality, availability, and licensing of suitable datasets.

---

## 📊 Dataset

The project will use publicly available and appropriately licensed dermatology datasets where possible.

---

## 📱 Android Application

The Android application will eventually allow users to provide a skin image and receive the model's prediction.

The Android development will include:

* 📱 Application interface
* 📸 Camera/image input
* 🖼️ Image selection
* 🧠 On-device model inference
* 📊 Prediction results
* 📋 Analysis history
* ⚠️ Appropriate user guidance and limitations

---

## 🗂️ Project Structure

The project structure will evolve as development progresses.

```text
SkinAI/
│
├── 📱 android/
│
├── 🧠 model/
│
├── 📊 dataset/
│
├── 📚 docs/
│
└── 📄 README.md
```

---

## 📜 Development Rules

To keep the project organized and prevent conflicts between team members, everyone must follow these rules.

### 🌿 1. Always Create a New Branch

**Never make changes directly on the `main` branch.**

Whenever you want to add a new feature, fix a bug, modify the model, update the UI, or make any other change:

```bash
git checkout main
git pull origin main
git checkout -b feature/your-feature-name
```

Examples:

```text
feature/android-layout
feature/dataset-preprocessing
feature/model-training
feature/camera-integration
fix/image-validation
docs/project-documentation
```

### 🔄 2. One Task = One Branch

Each GitHub Issue should have its own branch whenever practical.

```text
Issue #5
   ↓
feature/model-training
   ↓
Pull Request
   ↓
Review
   ↓
Merge
```

Do not combine unrelated tasks into the same branch.

### 🚫 3. Do Not Push Directly to `main`

All changes must go through a **Pull Request**.

```text
Your Branch
     ↓
Pull Request
     ↓
Code Review
     ↓
Approved ✅
     ↓
main
```

### 📝 4. Keep Commits Clear

Write meaningful commit messages that explain what changed.

Good:

```text
feat: add skin image preprocessing
feat: implement camera image selection
fix: handle invalid image input
docs: update dataset documentation
```

Avoid:

```text
update
changes
fix
final
test
asdf
```

### 🔗 5. Reference the GitHub Issue

Pull Requests and relevant commits should reference the GitHub Issue they are addressing.

Example:

```text
Closes #5
```

This allows GitHub to automatically associate the work with the corresponding issue.

### 👀 6. Review Before Merging

Before merging a Pull Request:

* 🧪 Test the changes.
* 🔍 Review the code.
* 📖 Check documentation.
* 🔗 Make sure the related issue is addressed.
* ⚠️ Check that existing functionality has not been broken.

### 🧹 7. Keep the Repository Clean

Do not commit:

* ❌ API keys
* ❌ Passwords
* ❌ Personal/private information
* ❌ Large temporary files
* ❌ Unnecessary generated files
* ❌ Local development configuration

Use `.gitignore` appropriately.

### 🤝 8. Keep Changes Focused

A Pull Request should focus on a specific task or issue.

Avoid mixing unrelated changes such as:

```text
❌ Model training
❌ UI redesign
❌ Documentation changes
❌ Random bug fixes
```

into one Pull Request unless they are directly related.

### 📚 9. Update Documentation

If a change affects how the project works, update the relevant documentation.

Documentation should be written in **English** and should be clear enough for another team member to understand the implementation.

### 🔀 10. Keep Your Branch Updated

Before creating or merging a Pull Request, make sure your branch is synchronized with the latest `main` branch.

```bash
git checkout main
git pull origin main
git checkout your-branch
git merge main
```

Resolve any conflicts before requesting the final review.

---

## 🔀 Git Workflow

The standard workflow for every task is:

```text
📋 GitHub Issue
      ↓
🌿 Create Branch
      ↓
💻 Implement Changes
      ↓
🧪 Test
      ↓
📤 Push Branch
      ↓
🔀 Create Pull Request
      ↓
👀 Code Review
      ↓
✅ Merge
      ↓
🧹 Delete Branch
```

**Main branch = stable project code.** 🛡️
