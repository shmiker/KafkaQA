package com.kafkaqa.repository;

import com.kafkaqa.dto.SwapDealIdentificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwapDealIdentificationDataReposotory extends JpaRepository<SwapDealIdentificationData, Integer> {

    SwapDealIdentificationData saveAndFlush(SwapDealIdentificationData swapDealIdentificationData);

    List<SwapDealIdentificationData> findAll();

}
