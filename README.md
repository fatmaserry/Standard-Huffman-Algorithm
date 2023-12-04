# Standard Huffman Compression/Decompression

This Java project implements the Standard Huffman compression algorithm and provides a simple graphical user interface (GUI) for compressing and decompressing text files.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Usage](#usage)

## Overview
The project consists of two main classes:

1. **Huffman**: This class contains the core implementation of the Standard Huffman compression algorithm. It includes methods for compressing and decompressing text files.

2. **HuffmanMainGUI**: This class provides a JavaFX-based graphical user interface for users to interact with the compression and decompression functionality. It uses the `Huffman` class to perform compression and decompression operations.

## Features
- **Compression**: Compresses text files using the Standard Huffman algorithm and saves the compressed data to a binary file (`compressed.bin`).
- **Decompression**: Decompresses binary files created by the compression process and saves the decompressed text to a new file (`decompressed.txt`).
- **Graphical User Interface**: Provides a user-friendly GUI with buttons for compression and decompression, along with visual feedback on the completion status.
   
## Usage
1. Launch the GUI application by running `HuffmanMainGUI.java`.
2. Click the "Compression" button to compress a text file (`.txt`).
3. Choose the desired text file to compress using the file dialog.
4. Wait for the compression process to complete, and view the status message.
5. Click the "Decompression" button to decompress a binary file (`.bin`).
6. Choose the compressed binary file using the file dialog.
7. Wait for the decompression process to complete, and view the status message.
