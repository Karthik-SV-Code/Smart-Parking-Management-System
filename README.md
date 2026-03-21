# 🚗 Smart Parking Management System

A desktop-based Smart Parking Management System developed using **Java Swing**, designed to efficiently manage parking slots, bookings, and user roles with a structured workflow.

---

## 📌 Features

- 🔐 **Role-Based Access Control**
  - Admin, User, and Security roles with separate functionalities

- 🅿️ **Parking Slot Management**
  - Add, remove, and monitor parking slots
  - View real-time availability status

- ⚙️ **Automatic Slot Allocation**
  - Implements a **first-fit algorithm** to assign available parking slots efficiently

- 📋 **Booking System**
  - Users can request parking slots
  - Booking status tracking (e.g., pending/verified)

- ✔️ **Verification Module**
  - Security/Admin can verify and manage booking requests

- 🖥️ **Dynamic User Interface**
  - Built using Java Swing with panel-based navigation (CardLayout)

- 💾 **Data Persistence**
  - Uses XML files for storing user data, parking slots, and bookings

---

## 🛠️ Tech Stack

- **Programming Language:** Java  
- **GUI Framework:** Java Swing  
- **Data Storage:** XML  
- **Architecture:** Modular (UI panels, models, and utility classes)

---

## 🧩 System Modules

- Authentication (Login/Register)
- Dashboard Navigation
- Slot Management (Admin)
- Booking Management (User)
- Verification System (Security/Admin)
- Profile Management

---

## 🚀 How It Works

1. Users log in based on their role (Admin/User/Security)
2. Users can view available parking slots and request booking
3. System assigns slots using a first-fit allocation approach
4. Admin/Security verifies booking requests
5. Slot availability updates dynamically in the interface

---

