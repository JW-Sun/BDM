package com.jw.bigwhalemonitor.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchedulingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SchedulingExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(String value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(String value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(String value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(String value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(String value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(String value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLike(String value) {
            addCriterion("uid like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotLike(String value) {
            addCriterion("uid not like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<String> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<String> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(String value1, String value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(String value1, String value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andScriptIdsIsNull() {
            addCriterion("script_ids is null");
            return (Criteria) this;
        }

        public Criteria andScriptIdsIsNotNull() {
            addCriterion("script_ids is not null");
            return (Criteria) this;
        }

        public Criteria andScriptIdsEqualTo(String value) {
            addCriterion("script_ids =", value, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsNotEqualTo(String value) {
            addCriterion("script_ids <>", value, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsGreaterThan(String value) {
            addCriterion("script_ids >", value, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsGreaterThanOrEqualTo(String value) {
            addCriterion("script_ids >=", value, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsLessThan(String value) {
            addCriterion("script_ids <", value, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsLessThanOrEqualTo(String value) {
            addCriterion("script_ids <=", value, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsLike(String value) {
            addCriterion("script_ids like", value, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsNotLike(String value) {
            addCriterion("script_ids not like", value, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsIn(List<String> values) {
            addCriterion("script_ids in", values, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsNotIn(List<String> values) {
            addCriterion("script_ids not in", values, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsBetween(String value1, String value2) {
            addCriterion("script_ids between", value1, value2, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andScriptIdsNotBetween(String value1, String value2) {
            addCriterion("script_ids not between", value1, value2, "scriptIds");
            return (Criteria) this;
        }

        public Criteria andCycleIsNull() {
            addCriterion("cycle is null");
            return (Criteria) this;
        }

        public Criteria andCycleIsNotNull() {
            addCriterion("cycle is not null");
            return (Criteria) this;
        }

        public Criteria andCycleEqualTo(Integer value) {
            addCriterion("cycle =", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotEqualTo(Integer value) {
            addCriterion("cycle <>", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThan(Integer value) {
            addCriterion("cycle >", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle >=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThan(Integer value) {
            addCriterion("cycle <", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThanOrEqualTo(Integer value) {
            addCriterion("cycle <=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleIn(List<Integer> values) {
            addCriterion("cycle in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotIn(List<Integer> values) {
            addCriterion("cycle not in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleBetween(Integer value1, Integer value2) {
            addCriterion("cycle between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle not between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andIntervalsIsNull() {
            addCriterion("intervals is null");
            return (Criteria) this;
        }

        public Criteria andIntervalsIsNotNull() {
            addCriterion("intervals is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalsEqualTo(Integer value) {
            addCriterion("intervals =", value, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsNotEqualTo(Integer value) {
            addCriterion("intervals <>", value, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsGreaterThan(Integer value) {
            addCriterion("intervals >", value, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsGreaterThanOrEqualTo(Integer value) {
            addCriterion("intervals >=", value, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsLessThan(Integer value) {
            addCriterion("intervals <", value, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsLessThanOrEqualTo(Integer value) {
            addCriterion("intervals <=", value, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsIn(List<Integer> values) {
            addCriterion("intervals in", values, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsNotIn(List<Integer> values) {
            addCriterion("intervals not in", values, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsBetween(Integer value1, Integer value2) {
            addCriterion("intervals between", value1, value2, "intervals");
            return (Criteria) this;
        }

        public Criteria andIntervalsNotBetween(Integer value1, Integer value2) {
            addCriterion("intervals not between", value1, value2, "intervals");
            return (Criteria) this;
        }

        public Criteria andMinuteIsNull() {
            addCriterion("minute is null");
            return (Criteria) this;
        }

        public Criteria andMinuteIsNotNull() {
            addCriterion("minute is not null");
            return (Criteria) this;
        }

        public Criteria andMinuteEqualTo(Integer value) {
            addCriterion("minute =", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteNotEqualTo(Integer value) {
            addCriterion("minute <>", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteGreaterThan(Integer value) {
            addCriterion("minute >", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("minute >=", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteLessThan(Integer value) {
            addCriterion("minute <", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteLessThanOrEqualTo(Integer value) {
            addCriterion("minute <=", value, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteIn(List<Integer> values) {
            addCriterion("minute in", values, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteNotIn(List<Integer> values) {
            addCriterion("minute not in", values, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteBetween(Integer value1, Integer value2) {
            addCriterion("minute between", value1, value2, "minute");
            return (Criteria) this;
        }

        public Criteria andMinuteNotBetween(Integer value1, Integer value2) {
            addCriterion("minute not between", value1, value2, "minute");
            return (Criteria) this;
        }

        public Criteria andHourIsNull() {
            addCriterion("hour is null");
            return (Criteria) this;
        }

        public Criteria andHourIsNotNull() {
            addCriterion("hour is not null");
            return (Criteria) this;
        }

        public Criteria andHourEqualTo(Integer value) {
            addCriterion("hour =", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourNotEqualTo(Integer value) {
            addCriterion("hour <>", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourGreaterThan(Integer value) {
            addCriterion("hour >", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourGreaterThanOrEqualTo(Integer value) {
            addCriterion("hour >=", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourLessThan(Integer value) {
            addCriterion("hour <", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourLessThanOrEqualTo(Integer value) {
            addCriterion("hour <=", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourIn(List<Integer> values) {
            addCriterion("hour in", values, "hour");
            return (Criteria) this;
        }

        public Criteria andHourNotIn(List<Integer> values) {
            addCriterion("hour not in", values, "hour");
            return (Criteria) this;
        }

        public Criteria andHourBetween(Integer value1, Integer value2) {
            addCriterion("hour between", value1, value2, "hour");
            return (Criteria) this;
        }

        public Criteria andHourNotBetween(Integer value1, Integer value2) {
            addCriterion("hour not between", value1, value2, "hour");
            return (Criteria) this;
        }

        public Criteria andWeekIsNull() {
            addCriterion("week is null");
            return (Criteria) this;
        }

        public Criteria andWeekIsNotNull() {
            addCriterion("week is not null");
            return (Criteria) this;
        }

        public Criteria andWeekEqualTo(String value) {
            addCriterion("week =", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotEqualTo(String value) {
            addCriterion("week <>", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThan(String value) {
            addCriterion("week >", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThanOrEqualTo(String value) {
            addCriterion("week >=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThan(String value) {
            addCriterion("week <", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThanOrEqualTo(String value) {
            addCriterion("week <=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLike(String value) {
            addCriterion("week like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotLike(String value) {
            addCriterion("week not like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekIn(List<String> values) {
            addCriterion("week in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotIn(List<String> values) {
            addCriterion("week not in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekBetween(String value1, String value2) {
            addCriterion("week between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotBetween(String value1, String value2) {
            addCriterion("week not between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andCronIsNull() {
            addCriterion("cron is null");
            return (Criteria) this;
        }

        public Criteria andCronIsNotNull() {
            addCriterion("cron is not null");
            return (Criteria) this;
        }

        public Criteria andCronEqualTo(String value) {
            addCriterion("cron =", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronNotEqualTo(String value) {
            addCriterion("cron <>", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronGreaterThan(String value) {
            addCriterion("cron >", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronGreaterThanOrEqualTo(String value) {
            addCriterion("cron >=", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronLessThan(String value) {
            addCriterion("cron <", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronLessThanOrEqualTo(String value) {
            addCriterion("cron <=", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronLike(String value) {
            addCriterion("cron like", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronNotLike(String value) {
            addCriterion("cron not like", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronIn(List<String> values) {
            addCriterion("cron in", values, "cron");
            return (Criteria) this;
        }

        public Criteria andCronNotIn(List<String> values) {
            addCriterion("cron not in", values, "cron");
            return (Criteria) this;
        }

        public Criteria andCronBetween(String value1, String value2) {
            addCriterion("cron between", value1, value2, "cron");
            return (Criteria) this;
        }

        public Criteria andCronNotBetween(String value1, String value2) {
            addCriterion("cron not between", value1, value2, "cron");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitIsNull() {
            addCriterion("repeat_submit is null");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitIsNotNull() {
            addCriterion("repeat_submit is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitEqualTo(Boolean value) {
            addCriterion("repeat_submit =", value, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitNotEqualTo(Boolean value) {
            addCriterion("repeat_submit <>", value, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitGreaterThan(Boolean value) {
            addCriterion("repeat_submit >", value, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("repeat_submit >=", value, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitLessThan(Boolean value) {
            addCriterion("repeat_submit <", value, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitLessThanOrEqualTo(Boolean value) {
            addCriterion("repeat_submit <=", value, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitIn(List<Boolean> values) {
            addCriterion("repeat_submit in", values, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitNotIn(List<Boolean> values) {
            addCriterion("repeat_submit not in", values, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitBetween(Boolean value1, Boolean value2) {
            addCriterion("repeat_submit between", value1, value2, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andRepeatSubmitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("repeat_submit not between", value1, value2, "repeatSubmit");
            return (Criteria) this;
        }

        public Criteria andExRestartIsNull() {
            addCriterion("ex_restart is null");
            return (Criteria) this;
        }

        public Criteria andExRestartIsNotNull() {
            addCriterion("ex_restart is not null");
            return (Criteria) this;
        }

        public Criteria andExRestartEqualTo(Boolean value) {
            addCriterion("ex_restart =", value, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartNotEqualTo(Boolean value) {
            addCriterion("ex_restart <>", value, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartGreaterThan(Boolean value) {
            addCriterion("ex_restart >", value, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ex_restart >=", value, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartLessThan(Boolean value) {
            addCriterion("ex_restart <", value, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartLessThanOrEqualTo(Boolean value) {
            addCriterion("ex_restart <=", value, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartIn(List<Boolean> values) {
            addCriterion("ex_restart in", values, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartNotIn(List<Boolean> values) {
            addCriterion("ex_restart not in", values, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartBetween(Boolean value1, Boolean value2) {
            addCriterion("ex_restart between", value1, value2, "exRestart");
            return (Criteria) this;
        }

        public Criteria andExRestartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ex_restart not between", value1, value2, "exRestart");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesIsNull() {
            addCriterion("waiting_batches is null");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesIsNotNull() {
            addCriterion("waiting_batches is not null");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesEqualTo(Integer value) {
            addCriterion("waiting_batches =", value, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesNotEqualTo(Integer value) {
            addCriterion("waiting_batches <>", value, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesGreaterThan(Integer value) {
            addCriterion("waiting_batches >", value, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesGreaterThanOrEqualTo(Integer value) {
            addCriterion("waiting_batches >=", value, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesLessThan(Integer value) {
            addCriterion("waiting_batches <", value, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesLessThanOrEqualTo(Integer value) {
            addCriterion("waiting_batches <=", value, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesIn(List<Integer> values) {
            addCriterion("waiting_batches in", values, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesNotIn(List<Integer> values) {
            addCriterion("waiting_batches not in", values, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesBetween(Integer value1, Integer value2) {
            addCriterion("waiting_batches between", value1, value2, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andWaitingBatchesNotBetween(Integer value1, Integer value2) {
            addCriterion("waiting_batches not between", value1, value2, "waitingBatches");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartIsNull() {
            addCriterion("blocking_restart is null");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartIsNotNull() {
            addCriterion("blocking_restart is not null");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartEqualTo(Boolean value) {
            addCriterion("blocking_restart =", value, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartNotEqualTo(Boolean value) {
            addCriterion("blocking_restart <>", value, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartGreaterThan(Boolean value) {
            addCriterion("blocking_restart >", value, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("blocking_restart >=", value, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartLessThan(Boolean value) {
            addCriterion("blocking_restart <", value, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartLessThanOrEqualTo(Boolean value) {
            addCriterion("blocking_restart <=", value, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartIn(List<Boolean> values) {
            addCriterion("blocking_restart in", values, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartNotIn(List<Boolean> values) {
            addCriterion("blocking_restart not in", values, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartBetween(Boolean value1, Boolean value2) {
            addCriterion("blocking_restart between", value1, value2, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andBlockingRestartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("blocking_restart not between", value1, value2, "blockingRestart");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeIsNull() {
            addCriterion("last_execute_time is null");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeIsNotNull() {
            addCriterion("last_execute_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeEqualTo(Date value) {
            addCriterion("last_execute_time =", value, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeNotEqualTo(Date value) {
            addCriterion("last_execute_time <>", value, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeGreaterThan(Date value) {
            addCriterion("last_execute_time >", value, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_execute_time >=", value, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeLessThan(Date value) {
            addCriterion("last_execute_time <", value, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_execute_time <=", value, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeIn(List<Date> values) {
            addCriterion("last_execute_time in", values, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeNotIn(List<Date> values) {
            addCriterion("last_execute_time not in", values, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeBetween(Date value1, Date value2) {
            addCriterion("last_execute_time between", value1, value2, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andLastExecuteTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_execute_time not between", value1, value2, "lastExecuteTime");
            return (Criteria) this;
        }

        public Criteria andSendEmailIsNull() {
            addCriterion("send_email is null");
            return (Criteria) this;
        }

        public Criteria andSendEmailIsNotNull() {
            addCriterion("send_email is not null");
            return (Criteria) this;
        }

        public Criteria andSendEmailEqualTo(Boolean value) {
            addCriterion("send_email =", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailNotEqualTo(Boolean value) {
            addCriterion("send_email <>", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailGreaterThan(Boolean value) {
            addCriterion("send_email >", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailGreaterThanOrEqualTo(Boolean value) {
            addCriterion("send_email >=", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailLessThan(Boolean value) {
            addCriterion("send_email <", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailLessThanOrEqualTo(Boolean value) {
            addCriterion("send_email <=", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailIn(List<Boolean> values) {
            addCriterion("send_email in", values, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailNotIn(List<Boolean> values) {
            addCriterion("send_email not in", values, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailBetween(Boolean value1, Boolean value2) {
            addCriterion("send_email between", value1, value2, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailNotBetween(Boolean value1, Boolean value2) {
            addCriterion("send_email not between", value1, value2, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksIsNull() {
            addCriterion("dingding_hooks is null");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksIsNotNull() {
            addCriterion("dingding_hooks is not null");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksEqualTo(String value) {
            addCriterion("dingding_hooks =", value, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksNotEqualTo(String value) {
            addCriterion("dingding_hooks <>", value, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksGreaterThan(String value) {
            addCriterion("dingding_hooks >", value, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksGreaterThanOrEqualTo(String value) {
            addCriterion("dingding_hooks >=", value, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksLessThan(String value) {
            addCriterion("dingding_hooks <", value, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksLessThanOrEqualTo(String value) {
            addCriterion("dingding_hooks <=", value, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksLike(String value) {
            addCriterion("dingding_hooks like", value, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksNotLike(String value) {
            addCriterion("dingding_hooks not like", value, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksIn(List<String> values) {
            addCriterion("dingding_hooks in", values, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksNotIn(List<String> values) {
            addCriterion("dingding_hooks not in", values, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksBetween(String value1, String value2) {
            addCriterion("dingding_hooks between", value1, value2, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andDingdingHooksNotBetween(String value1, String value2) {
            addCriterion("dingding_hooks not between", value1, value2, "dingdingHooks");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Boolean value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Boolean value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Boolean value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Boolean value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Boolean value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Boolean> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Boolean> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled not between", value1, value2, "enabled");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}