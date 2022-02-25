package com.shoppingsuite.service.impl;

import com.shoppingsuite.persistence.dao.DealRepo;
import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class DealServiceImpl implements DealService {
    @Autowired
    DealRepo dealRepo;

    @Override
    public Deal save(Deal deal) {
        return dealRepo.save(deal);
    }

    @Override
    public Optional<Deal> getById(Long id) {
        return dealRepo.findById(id);
    }

    @Override
    public boolean delete(Deal deal) {
        try{
            dealRepo.delete(deal);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            dealRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Page<Deal> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return dealRepo.findAll(pageable);
    }

    @Override
    public Page<Deal> getAllByStartDateAfter(int pageNo, int pageSize, Date startDate) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return dealRepo.findAllByStartDateAfterOrderByEndDateDesc(startDate, pageable);
    }

    @Override
    public Page<Deal> getAllByEndDateBefore(int pageNo, int pageSize, Date endDate) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return dealRepo.findAllByEndDateBeforeOrderByEndDateDesc(endDate, pageable);
    }


}
