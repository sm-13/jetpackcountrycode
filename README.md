# 📱 Jetpack Compose Country Code Picker

A fully custom-built **Country Code Picker** for Android using **Jetpack Compose** — with zero third-party libraries and total design freedom.

---

## 🚀 Why I Built This

While there are existing country pickers, most of them are:

- ❌ Hard to customize for specific UI needs
- ❌ Dependent on outdated or bloated libraries
- ❌ Not Jetpack Compose-friendly

So I built my own with:

- ✅ Full control over UI/UX
- ✅ Clean integration with Jetpack Compose
- ✅ Dynamic country data from a structured JSON file

---

## ✨ Features

- 🌍 **241 Countries Supported**
- 🇺🇳 Display country flags using emoji or custom SVG
- 📞 Show and select international dial codes (e.g., `+91`)
- 🔍 **Searchable** by country name or dial code
- 📏 **Phone number validation** based on country-specific length
- 🎨 Fully **customizable UI**

---

## 📁 JSON Data Structure

All country data is loaded from a single JSON file:

```json
[
    {
        "name": "India",
        "code": "IN",
        "emoji": "🇮🇳",
        "unicode": "U+1F1EE U+1F1F3",
        "image": "IN.svg",
        "dial_code": "+91",
        "phoneLength": 10
    }
    .....
]
```

---

## 📦 Tech Stack

- 🧠 **Kotlin**
- 🎨 **Jetpack Compose**
- 🗃 **JSON data parsing**
- 🗂 **Assets/Raw for country data**

---

## 🤝 Contributing

Pull requests and feature suggestions are welcome!  
If you find a bug or want a new feature, feel free to [open an issue](https://github.com/sm-13/jetpackcountrycode/issues).


https://github.com/user-attachments/assets/ae7b7905-3efc-4e90-a34d-eb3facb3ea73

