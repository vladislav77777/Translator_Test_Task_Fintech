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

//    private static final class TranslationRequestRowMapper implements RowMapper<TranslationRequest> {
//        @Override
//        public TranslationRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return new TranslationRequest(
//                    rs.getLong("id"),
//                    rs.getString("ip"),
//                    rs.getString("input"),
//                    rs.getString("result"),
//                    rs.getTimestamp("timestamp").toLocalDateTime()
//            );
//        }
//    }

    public void save(TranslationRequest request) {
        jdbcTemplate.update(ADD_QUERY,
                request.ip(), request.input(), request.result(), Timestamp.valueOf(request.timestamp())
        );
    }

    public List<TranslationRequest> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(TranslationRequest.class));
    }
}