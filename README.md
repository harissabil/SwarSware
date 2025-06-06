# SwarSware

SwarSware is an Android application designed to assist individuals with hearing impairments by detecting environmental sounds and converting them into structured vibrations in real-time. By leveraging LiteRT (formerly TensorFlow Lite) and foreground service technology, SwarSware can identify various emergency and situational sounds, providing proactive alerts through customizable vibration patterns.

This application enhances users’ safety and social participation in daily life, serving as an affordable alternative for those unable to purchase hearing aids (HAs), or as a complementary tool to increase environmental awareness for hearing aid users.

## Table of Contents
- [Demo Video](#demo-video)
- [Screenshots](#screenshots)
- [Key Features](#key-features)
- [Installation](#installation)

## Demo Video

https://github.com/user-attachments/assets/7842346f-2ca1-49c7-aa4a-5d7b3bea3b59

## Screenshots

<table>
  <tbody>
    <tr>
      <td><img src="assets/screenshot/ss_1.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_2.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_3.jpg?raw=true"/></td>
    </tr>
    <tr>
      <td><img src="assets/screenshot/ss_4.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_5.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_6.jpg?raw=true"/></td>
    </tr>
    <tr>
      <td><img src="assets/screenshot/ss_7.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_8.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_9.jpg?raw=true"/></td>
    </tr>
  </tbody>
</table>

## Key Features

1. On-Device Sound Classification — Utilizes the YAMNet model integrated directly on the device to detect and classify environmental sounds in real-time, without requiring an internet connection.
2. Priority Personalization — Users can adjust the priority level of different sound types, allowing for a customized experience tailored to their daily needs.
3. Emergency Contact Alert — Helps users automatically contact their designated emergency contacts when critical sounds are detected, improving safety by ensuring timely assistance.

## Installation

There are two options to install SwarSware:

### Option 1: Install from Google Drive

Visit this [link](https://drive.google.com/drive/folders/1NPU7ex2d-w7uCAk9EY8o8pt8e_jyYhZq?usp=sharing).

### Option 2: Build and Run from Source Code

1. Clone or download the project and open it in Android Studio.
2. Sync the project with Gradle and run the app using an emulator or a physical device.