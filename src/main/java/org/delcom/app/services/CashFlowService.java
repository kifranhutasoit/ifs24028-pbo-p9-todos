package org.delcom.app.services;

import org.delcom.app.entities.CashFlow;
import org.delcom.app.repositories.CashFlowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CashFlowService {

    private final CashFlowRepository cashFlowRepository;

    public CashFlowService(CashFlowRepository cashFlowRepository) {
        this.cashFlowRepository = cashFlowRepository;
    }

    public CashFlow createCashFlow(String type, String source, String label, Integer amount, String description) {
        CashFlow cashFlow = new CashFlow(type, source, label, amount, description);
        return cashFlowRepository.save(cashFlow);
    }

    public List<CashFlow> getAllCashFlows(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return cashFlowRepository.findByKeyword(search);
        }
        return cashFlowRepository.findAll();
    }

    public CashFlow getCashFlowById(UUID id) {
        return cashFlowRepository.findById(id).orElse(null);
    }

    public List<String> getCashFlowLabels() {
        return cashFlowRepository.findDistinctLabels();
    }

    public CashFlow updateCashFlow(UUID id, String type, String source, String label, Integer amount, String description) {
        CashFlow existingCashFlow = getCashFlowById(id);
        if (existingCashFlow == null) {
            return null;
        }
        existingCashFlow.setType(type);
        existingCashFlow.setSource(source);
        existingCashFlow.setLabel(label);
        existingCashFlow.setAmount(amount);
        existingCashFlow.setDescription(description);
        return cashFlowRepository.save(existingCashFlow);
    }

    public boolean deleteCashFlow(UUID id) {
        if (!cashFlowRepository.existsById(id)) {
            return false;
        }
        cashFlowRepository.deleteById(id);
        return true;
    }
}