package com.fatec.team1.TeachingPlatform.application.services;

import com.fatec.team1.TeachingPlatform.application.dto.UserAccountDTO;
import com.fatec.team1.TeachingPlatform.application.http.authentication.ProjectUserDetails;
import com.fatec.team1.TeachingPlatform.application.repositories.UserAccountRepository;
import com.fatec.team1.TeachingPlatform.domain.AccountRole;
import com.fatec.team1.TeachingPlatform.domain.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountService {

    private final UserAccountRepository repository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PasswordEncoder passwordEncoder;


    public UserAccountService(UserAccountRepository repository, PasswordEncoder passwordEncoder,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<UserAccountDTO> listAll() {
        return repository.findAll()
            .stream()
            .map(UserAccountDTO::new)
            .collect(Collectors.toList());
    }

    public List<UserAccountDTO> listAll(AccountRole accountRole) {
        return repository.findAll()
            .stream()
            .filter(userAccount -> userAccount.getRole().equals(accountRole))
            .map(UserAccountDTO::new)
            .collect(Collectors.toList());
    }

    public UserAccount findById(Long id) {
        return repository.findById(id).get();
    }

    public UserAccount findByEmail(String email) {
        return repository.findByEmail(email).get();
    }

    public void save(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        repository.save(userAccount);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void changePassword(String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((ProjectUserDetails) (authentication).getPrincipal()).getUsername();

        UserAccount userAccount = findByEmail(email);
        userAccount.setPassword(passwordEncoder.encode(newPassword));

        repository.save(userAccount);

    }

    public boolean checkPassword(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((ProjectUserDetails) (authentication).getPrincipal()).getUsername();

        UserAccount userAccount = findByEmail(email);

        return passwordEncoder.matches(password, userAccount.getPassword());
    }

}
