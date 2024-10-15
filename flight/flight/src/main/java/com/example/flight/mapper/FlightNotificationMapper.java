package com.example.flight.mapper;

import com.example.flight.dto.FlightNotificationDto;
import com.example.flight.entity.FlightNotification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightNotificationMapper {
    FlightNotificationDto toDto(FlightNotification entity);

    FlightNotification toEntity(FlightNotificationDto dto);

    List<FlightNotificationDto> toDtoList(List<FlightNotification> entities);

    List<FlightNotification> toEntityList(List<FlightNotificationDto> dtos);
}
