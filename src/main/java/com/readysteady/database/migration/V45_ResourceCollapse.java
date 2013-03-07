package com.readysteady.database.migration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.googlecode.flyway.core.api.migration.spring.SpringJdbcMigration;

public class V45_ResourceCollapse implements SpringJdbcMigration {

	private static class ResourceHolder {
		String id;
		String serverId;
	}

	@Override
	public void migrate(final JdbcTemplate jdbcTemplate) throws Exception {
		jdbcTemplate.execute("alter table Resource add column serverId varchar(255);");
		final List<ResourceHolder> list = jdbcTemplate.query("select id, serverId from VirtualResource", new RowMapper<ResourceHolder>() {
			@Override
			public ResourceHolder mapRow(final ResultSet rs, final int rowNum) throws SQLException {
				final ResourceHolder r = new ResourceHolder();
				r.id = rs.getString("id");
				r.serverId = rs.getString("serverId");
				return r;
			}
		});
		for (final ResourceHolder r : list) {
			jdbcTemplate.update("update Resource set serverId = ? where id = ?", new Object[] { r.serverId, r.id });
		}

	}
}
