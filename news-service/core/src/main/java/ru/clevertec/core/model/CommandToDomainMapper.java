package ru.clevertec.core.model;

import org.mapstruct.Mapper;
import ru.clevertec.core.ports.in.command.CreateNewsCommand;
import ru.clevertec.core.ports.in.command.UpdateNewsCommand;

@Mapper(componentModel = "spring")
public interface CommandToDomainMapper {
    News createNewsCommandToDomain(CreateNewsCommand createNewsCommand);
    News updateNewsCommandToDomain(UpdateNewsCommand updateNewsCommand);
}
