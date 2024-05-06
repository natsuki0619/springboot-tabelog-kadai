package com.example.nagoyameshi.event;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.repository.VerificationTokenRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ResetEventListener {
    private final VerificationTokenRepository verificationTokenRepository;
    private final JavaMailSender javaMailSender;
    private String confirmationUrl;

    public ResetEventListener(VerificationTokenRepository verificationTokenRepository, JavaMailSender mailSender) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.javaMailSender = mailSender;
        log.info("confirmationUrl:" + confirmationUrl);
    }

    @EventListener
    private void onResetEvent(ResetEvent resetEvent) {
        User user = resetEvent.getUser();
        Optional<VerificationToken> optionalVerificationToken = Optional
                .ofNullable(verificationTokenRepository.findByUserId(user.getId()));

        if (optionalVerificationToken.isPresent()) {
            VerificationToken verificationToken = optionalVerificationToken.get();
            String token = verificationToken.getToken();

            String recipientAddress = user.getEmail();
            String subject = "メール認証";
            String confirmationUrl = resetEvent.getRequestUrl() + "/verify?token=" + token;
            String message = "以下のリンクをクリックしてパスワードを再発行してください。";

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(recipientAddress);
            mailMessage.setSubject(subject);
            mailMessage.setText(message + "\n" + confirmationUrl);
            javaMailSender.send(mailMessage);
        } else {
            log.error("Verification token not found for user: " + user.getId());
        }
    }
}