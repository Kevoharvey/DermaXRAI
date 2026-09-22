<img width="1920" height="1080" alt="Derma-X-R{AI}" src="https://github.com/user-attachments/assets/d84b52d2-d3ba-4569-837f-0fe47adb7a0a" />

# DermaXR{AI}

> The new step for Image Diagnosing Models.
<img src="https://custom-icon-badges.demolab.com/badge/Status-In%20Progress-yellow?logoColor=fff" alt="Status: In Progress" height="40">
<p align="center">
  <img src="https://custom-icon-badges.demolab.com/badge/Kotlin-7F52FF?logo=kotlin&logoColor=fff" alt="Kotlin" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/Android-3DDC84?logo=android&logoColor=fff" alt="Android" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/PyTorch-EE4C2C?logo=pytorch&logoColor=fff" alt="PyTorch" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/Scikit--learn-F7931E?logo=scikit-learn&logoColor=fff" alt="Scikit-learn" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/NumPy-013243?logo=numpy&logoColor=fff" alt="NumPy" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/Pandas-150458?logo=pandas&logoColor=fff" alt="Pandas" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/Matplotlib-71D291?logo=matplotlib&logoColor=fff" alt="Matplotlib" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/Seaborn-4C72B0?logo=seaborn&logoColor=fff" alt="Seaborn" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/OpenCV-5C3EE8?logo=opencv&logoColor=fff" alt="OpenCV" height="40">
  <img src="https://custom-icon-badges.demolab.com/badge/Pillow-3776AB?logo=pillow&logoColor=fff" alt="Pillow" height="40">
</p>

An AI-powered skincare application that uses Deep Learning to analyze skin images and identify potential skin conditions.

---

## 📌 About the Project

**DermaXR{AI}** is an Android application project focused on analyzing skin images and providing users with information about potential skin conditions.

The project combines:

* 🧠 **Deep Learning** for skin-condition image classification
* 📱 **Android** for the mobile application
* 📸 **Image Analysis** for user-provided skin images
* 🎨 **UI/UX Design** using Google Stitch and Figma
* 📊 **Machine Learning Evaluation** to measure model performance

> ⚠️ **Disclaimer:** DermaXR{AI} is an educational/research project and is not intended to provide a definitive medical diagnosis. Model predictions should not replace professional medical advice.

---

## 🎯 Project Goals

The main goals of DermaXR{AI} are to:

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

### 🔥 Framework

The Deep Learning component will be developed using **PyTorch**.

---

## 📊 Dataset

The project will use **Derma1M** and other publicly available dermatology datasets where possible.

> ⚠️ Dataset usage must follow the applicable licensing and usage terms.

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
DermaXR{AI}/
│
├── 📱 android/
├── 🧠 model/
├── 📊 dataset/
├── 📚 docs/
└── 📄 README.md
```

---

## ⚙️ Project Setup

### 📥 1. Clone the Repository

Open **VS Code** and:

1. Open **Source Control**.
2. Select **Clone Repository**.
3. Enter the GitHub repository URL.
4. Select the location for the project.
5. Open the cloned repository in VS Code.

### 🌿 2. Switch to `dev`

The project uses **`dev`** as the main development branch.

In VS Code:

1. Click the branch name in the bottom-left corner.
2. Select **`dev`**.
3. Use **Source Control → Pull** to get the latest changes.

> ⚠️ **Do not work directly on `main`.**

### 🐍 3. Set Up Python

In VS Code:

1. Open the Command Palette.
2. Select **Python: Create Environment**.
3. Select **Venv**.
4. Select the project's Python version.
5. Select `requirements.txt` when prompted.

### 📦 4. Requirements

Create or use `requirements.txt`:

```txt
numpy
pandas
matplotlib
seaborn
scikit-learn
jupyter
opencv-python
Pillow
torch
torchvision
torchaudio
```

### 🧪 5. Verify PyTorch

Run the following in Python:

```python
import torch

print(f"PyTorch: {torch.__version__}")
print(
    f"Device: {'MPS' if torch.backends.mps.is_available() else 'CUDA' if torch.cuda.is_available() else 'CPU'}"
)
```

---

## 📜 Development Rules

To keep the project organized and prevent conflicts between team members, everyone must follow these rules.

### 🌿 1. Always Create a New Branch

**Never make changes directly on the `main` or `dev` branch.**

Before starting a task:

1. Make sure you are on **`dev`**.
2. Pull the latest changes.
3. Open **Source Control**.
4. Select the current branch.
5. Choose **Create New Branch**.
6. Create a branch for your GitHub Issue.

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
Merge into dev
```

Do not combine unrelated tasks into the same branch.

### 🚫 3. Do Not Push Directly to `main` or `dev`

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
dev
```

After final testing, `dev` can be merged into `main`.

### 📝 4. Keep Commits Clear

Write meaningful commit messages that explain what changed.

**Good:**

```text
feat: add skin image preprocessing
feat: implement camera image selection
fix: handle invalid image input
docs: update dataset documentation
```

**Avoid:**

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

Before creating a Pull Request:

1. Switch to **`dev`** in VS Code.
2. Pull the latest changes.
3. Return to your feature branch.
4. Use the VS Code **Source Control** options to merge/rebase the latest `dev` changes into your branch.
5. Resolve any conflicts.
6. Test your changes.
7. Push the updated branch.

---

## 🔀 Git Workflow

The standard workflow for every task is:

```text
📋 GitHub Issue
      ↓
🌿 Create Branch from dev
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
✅ Merge into dev
      ↓
🧪 Integration Testing
      ↓
🚀 Merge into main
      ↓
🧹 Delete Branch
```

### 🌳 Branch Structure

```text
main
 │
 │  Stable project code
 │
 └── dev
      │
      │  Active development
      │
      ├── feature/model-training
      ├── feature/dataset-preprocessing
      ├── feature/android-layout
      ├── feature/camera-integration
      └── fix/image-validation
```

**`main` = stable project code.** 🛡️

**`dev` = active development and integration.** 🚧
