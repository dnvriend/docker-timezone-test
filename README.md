# docker-timezone-test
Shows how to package your docker application and how to handle timezones and time correctly.

# Postgres
To find out the timezone its using:

```sql
show timezone;
> Europe/Amsterdam

select current_timestamp;
> 2015-04-22 08:07:32.986855

-- format as ISO-8601
select to_char(now() at time zone 'UTC', 'YYYY-MM-DDHH"T"24:MM:SS.MS"Z"')
> 2015-04-2206T24:04:55.144Z
```

* [PostgreSQL and ISO 8601 Timestamps](http://lluad.com/blog/2013/08/27/postgresql-and-iso-8601-timestamps/)
