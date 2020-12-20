package com.example.backend.service;

public interface MailService {
    void sendSimpleEmai(String to, String subject, String content);
}

