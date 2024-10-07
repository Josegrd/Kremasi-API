package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.constant.EInstallmentType;
import com.gardio.kremasi.entity.InstallmentType;
import com.gardio.kremasi.model.request.InstallmentTypeRequest;
import com.gardio.kremasi.model.response.InstallmentTypeResponse;
import com.gardio.kremasi.repository.InstallmentTypeRepository;
import com.gardio.kremasi.service.InstallmentTypeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstallmentTypeServiceImpl implements InstallmentTypeService {
    private final InstallmentTypeRepository installmentTypeRepository;

    @PostConstruct
    public void initInstallmentType(){
        for(EInstallmentType installmentType : EInstallmentType.values()){
            String name = installmentType.name();
            Optional<InstallmentType> optionalInstallmentType = installmentTypeRepository.findByInstallmentType(installmentType);
            if (optionalInstallmentType.isEmpty()) {
                InstallmentType newInstallmentType = InstallmentType.builder()
                        .installmentType(installmentType)
                        .build();
                installmentTypeRepository.saveAndFlush(newInstallmentType);
            }
        }
    }

    @Override
    public InstallmentTypeResponse createInstallmentType(InstallmentTypeRequest request) {
        try{
            Optional<InstallmentType> optionalInstallmentType = installmentTypeRepository.findByInstallmentType(request.getInstallmentType());
            if (optionalInstallmentType.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Installment type already exist");
            }
            InstallmentType installmentType = InstallmentType.builder()
                    .installmentType(request.getInstallmentType())
                    .build();
            installmentTypeRepository.save(installmentType);
            return convertToResponse(installmentType);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public InstallmentTypeResponse getInstallmentTypeById(String id) {
        return convertToResponse(findByIdOrThrowNotFoundException(id));
    }

    @Override
    public List<InstallmentTypeResponse> getAllInstallmentType() {
        return installmentTypeRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    @Override
    public InstallmentTypeResponse updateInstallmentType(InstallmentTypeRequest request) {
        try{
            InstallmentType installmentType = findByIdOrThrowNotFoundException(request.getId());
            installmentType.setInstallmentType(request.getInstallmentType());
            installmentTypeRepository.save(installmentType);
            return convertToResponse(installmentType);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void deleteInstallmentType(String id) {
        installmentTypeRepository.deleteById(id);
    }

    @Override
    public Page<InstallmentType> getAllInstallmentType(int page, int size) {
        if(page <= 0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        return installmentTypeRepository.findAll(pageable);
    }

    @Override
    public InstallmentType getById(String id) {
        return installmentTypeRepository.findById(id).orElse(null);
    }

    public InstallmentType findByIdOrThrowNotFoundException(String id) {
        return installmentTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Installment type not found"));
    }

    private InstallmentTypeResponse convertToResponse(InstallmentType installmentType) {
        return InstallmentTypeResponse.builder()
                .id(installmentType.getId())
                .installmentType(installmentType.getInstallmentType())
                .build();
    }
}
