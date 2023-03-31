package com.example.user.service.seller;


import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Seller;
import com.example.user.domain.repository.SellerRepository;
import com.example.user.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.user.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class SignUpSellerService {
    private final SellerRepository sellerRepository;


    public Seller signUp(SignUpForm form) {
        return sellerRepository.save(Seller.from(form));
    }


    public boolean isEmailExist(String email) {
        return sellerRepository.findByEmail(email).isPresent();
    }


    @Transactional
    public void verifyEmail(String email, String code) {
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (seller.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);
        } else if (!seller.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);
        } else if (seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }

        seller.setVerify(true);
    }


    @Transactional
    public LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode) {
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);

        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            seller.setVerificationCode(verificationCode);
            seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return seller.getVerifyExpiredAt();
        }

        throw new CustomException(NOT_FOUND_USER);
    }
}
