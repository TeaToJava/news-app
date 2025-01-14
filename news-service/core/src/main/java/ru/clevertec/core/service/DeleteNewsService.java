package ru.clevertec.core.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.ports.in.DeleteNewsUseCase;
import ru.clevertec.core.ports.in.command.DeleteNewsCommand;
import ru.clevertec.core.ports.out.DeleteNewsPort;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteNewsService implements DeleteNewsUseCase {

    private final DeleteNewsPort deleteNewsPort;
    private final CommandToDomainMapper commandToDomainMapper;

    @Override
    @Transactional
    public void deleteNews(DeleteNewsCommand deleteNewsCommand) {
        UUID id = deleteNewsCommand.id();
        deleteNewsPort.deleteNewsComments(id);
        deleteNewsPort.deleteNews(id);
    }
}
