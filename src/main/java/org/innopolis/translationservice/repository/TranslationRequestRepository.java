package org.innopolis.translationservice.repository;

import lombok.RequiredArgsConstructor;
import org.innopolis.translationservice.model.TranslationRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TranslationRequestRepository {
    private static final String ADD_QUERY = "INSERT INTO translation_request (ip, input, result, timestamp) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM translation_request";

    private final JdbcTemplate jdbcTemplate;

    public void save(TranslationRequest request) {
        jdbcTemplate.update(ADD_QUERY,
                request.ip(), request.input(), request.result(), Timestamp.valueOf(request.timestamp())
        );
    }

    public List<TranslationRequest> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(TranslationRequest.class));
    }
}