package org.delcom.app.controllers;

import org.delcom.app.configs.ApiResponse;
import org.delcom.app.entities.CashFlow;
import org.delcom.app.services.CashFlowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cash-flows")
public class CashFlowController {

    private final CashFlowService cashFlowService;

    public CashFlowController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    private boolean isInvalid(CashFlow cf) {
        return cf.getType() == null || cf.getType().isBlank() ||
               cf.getSource() == null || cf.getSource().isBlank() ||
               cf.getLabel() == null || cf.getLabel().isBlank() ||
               cf.getAmount() == null || cf.getAmount() <= 0 ||
               cf.getDescription() == null || cf.getDescription().isBlank();
    }

    @PostMapping
    public ApiResponse<Map<String, UUID>> createCashFlow(@RequestBody CashFlow cashFlow) {
        if (isInvalid(cashFlow)) {
            return new ApiResponse<>("fail", "Data tidak valid");
        }
        CashFlow created = cashFlowService.createCashFlow(
            cashFlow.getType(), cashFlow.getSource(), cashFlow.getLabel(),
            cashFlow.getAmount(), cashFlow.getDescription()
        );
        return new ApiResponse<>("success", "Berhasil menambahkan data", Map.of("id", created.getId()));
    }

    @GetMapping
    public ApiResponse<Map<String, List<CashFlow>>> getAllCashFlows(@RequestParam(required = false) String search) {
        List<CashFlow> cashFlows = cashFlowService.getAllCashFlows(search);
        return new ApiResponse<>("success", "Berhasil mengambil data", Map.of("cash_flows", cashFlows));
    }

    @GetMapping("/{id}")
    public ApiResponse<Map<String, CashFlow>> getCashFlowById(@PathVariable UUID id) {
        CashFlow cashFlow = cashFlowService.getCashFlowById(id);
        if (cashFlow == null) {
            return new ApiResponse<>("fail", "Data tidak ditemukan");
        }
        return new ApiResponse<>("success", "Berhasil mengambil data", Map.of("cashFlow", cashFlow));
    }
    
    @GetMapping("/labels")
    public ApiResponse<Map<String, List<String>>> getCashFlowLabels() {
        List<String> labels = cashFlowService.getCashFlowLabels();
        return new ApiResponse<>("success", "Berhasil mengambil data", Map.of("labels", labels));
    }

    @PutMapping("/{id}")
    public ApiResponse<CashFlow> updateCashFlow(@PathVariable UUID id, @RequestBody CashFlow cashFlow) {
        if (isInvalid(cashFlow)) {
            return new ApiResponse<>("fail", "Data tidak valid", null);
        }
        CashFlow updated = cashFlowService.updateCashFlow(
            id, cashFlow.getType(), cashFlow.getSource(), cashFlow.getLabel(),
            cashFlow.getAmount(), cashFlow.getDescription()
        );
        if (updated == null) {
            return new ApiResponse<>("fail", "Gagal memperbarui data, ID tidak ditemukan", null);
        }
        return new ApiResponse<>("success", "Berhasil memperbarui data");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCashFlow(@PathVariable UUID id) {
        boolean deleted = cashFlowService.deleteCashFlow(id);
        if (!deleted) {
            return new ApiResponse<>("fail", "Gagal menghapus data, ID tidak ditemukan");
        }
        return new ApiResponse<>("success", "Berhasil menghapus data");
    }
}