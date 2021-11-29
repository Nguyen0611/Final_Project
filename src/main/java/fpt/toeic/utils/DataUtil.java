package fpt.toeic.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import fpt.toeic.config.Constants;
import fpt.toeic.domain.ExcelColumn;
import fpt.toeic.service.dto.ConfigReportColumnDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataUtil {


//    public static String sqlInjection(String str) {
//        if (StringUtils.isEmpty(str)) return str;
//        str = str.trim().toLowerCase()
//                .replace("%", Constants.DEFAULT_ESCAPE_CHAR + "%")
//                .replace("_", Constants.DEFAULT_ESCAPE_CHAR + "_");
//        return str;
//    }

    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);

    private static final String PHONE_PATTERN = "^[0-9]*$";
    static private String saltSHA256 = "1";
    static private String AES = "AES";
    static private String DES = "DES";
    private static final String MISS_ENVIREMENT_SETTING = "{0} must be set in environment variable";
    static final String YYYY_PT = "yyyy";
    static final String YYYYmm_PT = "yyyyMM";
    private static final String YYYYMMDD = "yyyyMMdd";

    /**
     * Copy du lieu tu bean sang bean moi
     * Luu y chi copy duoc cac doi tuong o ngoai cung, list se duoc copy theo tham chieu
     * <p>
     * Chi dung duoc cho cac bean java, khong dung duoc voi cac doi tuong dang nhu String, Integer, Long...
     *
     * @param source
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneBean(T source) {
        try {
            if (source == null) {
                return null;
            }
            T dto = (T) source.getClass().getConstructor().newInstance();
            BeanUtils.copyProperties(source, dto);
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
//    public static String getSafeFileName(String fileName) {
//        return fileName == null ? "" : fileName.replaceAll("\\s+","_");
//    }

    public static String getSafeFileName(String fileName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fileName.length(); i++) {
            char c = fileName.charAt(i);
            if (c != '/' && c != '\\' && c != 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }

    public static boolean isNullOrZero(Integer value) {
        return (value == null || value.equals(0));
    }

    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */


    /**
     * Upper first character
     *
     * @param input
     * @return
     */
    public static String upperFirstChar(String input) {
        if (DataUtil.isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }


    public static Long safeToLong(Object obj1, Long defaultValue) {
        Long result = defaultValue;
        if (obj1 != null) {
            if (obj1 instanceof BigDecimal) {
                return ((BigDecimal) obj1).longValue();
            }
            if (obj1 instanceof BigInteger) {
                return ((BigInteger) obj1).longValue();
            }
            try {
                result = Long.parseLong(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return Long
     */
    public static Long safeToLong(Object obj1) {
        return safeToLong(obj1, null);
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        Double result = defaultValue;
        if (obj1 != null) {
            try {
                result = Double.parseDouble(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }


    public static Short safeToShort(Object obj1, Short defaultValue) {
        Short result = defaultValue;
        if (obj1 != null) {
            try {
                result = Short.parseShort(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1
     * @param defaultValue
     * @return
     * @author phuvk
     */
    public static int safeToInt(Object obj1, int defaultValue) {
        int result = defaultValue;
        if (obj1 != null) {
            try {
                result = Integer.parseInt(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return int
     */
    public static int safeToInt(Object obj1) {
        return safeToInt(obj1, 0);
    }


    /**
     * @param obj1 Object
     * @return String
     */
    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null || obj1.toString().isEmpty()) {
            return defaultValue;
        }

        return obj1.toString();
    }

    public static Boolean safeToBoolean(Object obj1) {
        if (obj1 == null || obj1 instanceof Boolean) {
            return (Boolean) obj1;
        }
        return false;
    }


    /**
     * @param obj1 Object
     * @return String
     */
    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }


    /**
     * safe equal
     *
     * @param obj1 String
     * @param obj2 String
     * @return boolean
     */
    public static boolean safeEqual(String obj1, String obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && obj1.equals(obj2));
    }

    /**
     * check null or empty
     * Su dung ma nguon cua thu vien StringUtils trong apache common lang
     *
     * @param cs String
     * @return boolean
     */
    public static boolean isNullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(final Object obj) {
        return obj == null || obj.toString().isEmpty();
    }

    public static boolean isNullOrEmpty(final Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Ham nay mac du nhan tham so truyen vao la object nhung gan nhu chi hoat dong cho doi tuong la string
     * Chuyen sang dung isNullOrEmpty thay the
     *
     * @param obj1
     * @return
     */
    @Deprecated
    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || "".equals(obj1.toString().trim());
    }

    public static BigInteger length(BigInteger from, BigInteger to) {
        return to.subtract(from).add(BigInteger.ONE);
    }

    public static BigDecimal add(BigDecimal number1, BigDecimal number2, BigDecimal... numbers) {
        List<BigDecimal> realNumbers = Lists.newArrayList(number1, number2);
        if (!DataUtil.isNullOrEmpty(numbers)) {
            Collections.addAll(realNumbers, numbers);
        }
        return realNumbers.stream()
                .filter(x -> x != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Long add(Long number1, Long number2, Long... numbers) {
        List<Long> realNumbers = Lists.newArrayList(number1, number2);
        if (!DataUtil.isNullOrEmpty(numbers)) {
            Collections.addAll(realNumbers, numbers);
        }
        return realNumbers.stream()
                .filter(x -> x != null)
                .reduce(0L, (x, y) -> x + y);
    }

    /**
     * add
     *
     * @param obj1 BigDecimal
     * @param obj2 BigDecimal
     * @return BigDecimal
     */
    public static BigInteger add(BigInteger obj1, BigInteger obj2) {
        if (obj1 == null) {
            return obj2;
        } else if (obj2 == null) {
            return obj1;
        }

        return obj1.add(obj2);
    }


    /**
     * Collect values of a property from an object list instead of doing a for:each then call a getter
     * Consider using stream -> map -> collect of java 8 instead
     *
     * @param source       object list
     * @param propertyName name of property
     * @param returnClass  class of property
     * @return value list of property
     */
    @Deprecated
    public static <T> List<T> collectProperty(Collection<?> source, String propertyName, Class<T> returnClass) {
        List<T> propertyValues = Lists.newArrayList();
        try {
            String getMethodName = "toLocale" + upperFirstChar(propertyName);
            for (Object x : source) {
                Class<?> clazz = x.getClass();
                Method getMethod = clazz.getMethod(getMethodName);
                Object propertyValue = getMethod.invoke(x);
                if (propertyValue != null && returnClass.isAssignableFrom(propertyValue.getClass())) {
                    propertyValues.add(returnClass.cast(propertyValue));
                }
            }
            return propertyValues;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    /**
     * Collect distinct values of a property from an object list instead of doing a for:each then call a getter
     * Consider using stream -> map -> collect of java 8 instead
     *
     * @param source       object list
     * @param propertyName name of property
     * @param returnClass  class of property
     * @return value list of property
     */
    @Deprecated
    public static <T> Set<T> collectUniqueProperty(Collection<?> source, String propertyName, Class<T> returnClass) {
        List<T> propertyValues = collectProperty(source, propertyName, returnClass);
        return Sets.newHashSet(propertyValues);
    }

    public static boolean isNullObject(Object obj1) {
        if (obj1 == null) {
            return true;
        }
        if (obj1 instanceof String) {
            return isNullOrEmpty(obj1.toString());
        }
        return false;
    }


    public static boolean isCollection(Object ob) {
        return ob instanceof Collection || ob instanceof Map;
    }

    public static String makeLikeParam(String s) {
        if (StringUtils.isEmpty(s)) return null;
        s = s.trim().toLowerCase().replace("!", Constants.DEFAULT_ESCAPE_CHAR + "!")
                .replace("%", Constants.DEFAULT_ESCAPE_CHAR + "%")
                .replace("&", Constants.DEFAULT_ESCAPE_CHAR + "&")
                .replace("_", Constants.DEFAULT_ESCAPE_CHAR + "_");
        return "%" + s + "%";
    }

    public static String makeLikeQuery(String s) {
        if (StringUtils.isEmpty(s)) return null;
        s = s.trim().toLowerCase().replace("!", Constants.DEFAULT_ESCAPE_CHAR_QUERY + "!")
                .replace("%", Constants.DEFAULT_ESCAPE_CHAR_QUERY + "%")
                .replace("_", Constants.DEFAULT_ESCAPE_CHAR_QUERY + "_");
        return "%" + s + "%";
    }

    /**
     * @param date
     * @param format yyyyMMdd, yyyyMMddhhmmss,yyyyMMddHHmmssSSS only
     * @return
     */
    public static Integer getDateInt(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return Integer.parseInt(dateStr);
    }

    /**
     * @param date
     * @param format yyyyMMdd, yyyyMMddhhmmss,yyyyMMddHHmmssSSS only
     * @return
     */
    public static Long getDateLong(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return Long.parseLong(dateStr);
    }

//    public static Integer getAbsoluteDate(Integer date, Integer relativeTime, Integer timeType) throws ParseException {
//        if (date == null) return 0;
//        if (relativeTime == null) return date;
//        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_YYYYMMDD);
//        Date newDate = sdf.parse(date.toString());
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(newDate);
//        if (Constants.TIME_TYPE_DATE.equals(timeType) || Constants.TIME_TYPE_DATE.toString().equals(timeType)) {
//            cal.add(Calendar.DATE, relativeTime);
//        } else if (Constants.TIME_TYPE_MONTH.equals(timeType) || Constants.TIME_TYPE_MONTH.toString().equals(timeType)) {
//            cal.add(Calendar.MONTH, relativeTime);
//        } else if (Constants.TIME_TYPE_QUARTER.equals(timeType) || Constants.TIME_TYPE_QUARTER.toString().equals(timeType)) {
//            cal.add(Calendar.MONTH, relativeTime * 3);
//        } else if (Constants.TIME_TYPE_YEAR.equals(timeType) || Constants.TIME_TYPE_YEAR.toString().equals(timeType)) {
//            cal.add(Calendar.YEAR, relativeTime);
//        }
//        return Integer.parseInt(sdf.format(cal.getTime()));
//    }

    private static void resetTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
    }

    public static Date getFirstDateOfMonth(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        resetTime(cal);
        return cal.getTime();
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        resetTime(cal);
        return cal.getTime();
    }

    public static Date getFirstDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //Thang 1 thi calendar.MONTH = 0
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        resetTime(cal);
        return cal.getTime();
    }

//    public static Date getAbsoluteDate(Date date, Integer relativeTime, Object timeType) {
//        if (relativeTime == null) return date;
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        if (TimeType.FORTUITY.value().equals(timeType) || TimeType.FORTUITY.value().toString().equals(timeType)) {
//            cal.add(Calendar.DATE, relativeTime);
//        } else if (TimeType.MONTH.value().equals(timeType) || TimeType.MONTH.value().toString().equals(timeType)) {
//            cal.add(Calendar.MONTH, relativeTime);
//        } else if (TimeType.QUARTER.value().equals(timeType) || TimeType.QUARTER.value().toString().equals(timeType)) {
//            cal.add(Calendar.MONTH, (relativeTime) * 3);
//        } else if (TimeType.YEAR.value().equals(timeType) || TimeType.YEAR.value().toString().equals(timeType)) {
//            cal.add(Calendar.YEAR, relativeTime);
//        }
//        return cal.getTime();
//    }

    public static boolean isDate(String str, String format) {
        if (StringUtils.isEmpty(str)) return false;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(str);
            return str.equals(sdf.format(date));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static Date getDatePattern(String date, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    public static String formatDatePattern(Integer prdId, String pattern) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
            Date date = sdf.parse(prdId.toString());

            SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
            result = sdf2.format(date);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    /**
     * Build list column xuat file
     *
     * @param lstColumnCurrent
     * @return
     */
    public static List<ExcelColumn> getListExcelColumn(List<ConfigReportColumnDTO> lstColumnCurrent) {
        List<ExcelColumn> lstColumn = lstColumnCurrent.stream().map(col -> {
            ExcelColumn excelColumn = new ExcelColumn();
            excelColumn.setColumn(col.getColumnName());
            excelColumn.setTitle(col.getTitle());
            if (Constants.DATA_TYPE.STRING.equalsIgnoreCase(col.getDataType())) {
                excelColumn.setAlign(ExcelColumn.ALIGN_MENT.LEFT);
            } else if (Constants.DATA_TYPE.DATE.equalsIgnoreCase(col.getDataType())) {
                excelColumn.setAlign(ExcelColumn.ALIGN_MENT.CENTER);
            } else {
                excelColumn.setAlign(ExcelColumn.ALIGN_MENT.RIGHT);
            }
            return excelColumn;
        }).collect(Collectors.toList());
        return lstColumn;
    }

    public static String formatQuarterPattern(Integer prdId) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
            Date date = sdf.parse(prdId.toString());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            String result2 = sdf2.format(date);

            result = (date.getMonth() / 3 + 1) + "/" + result2;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    public static String formatDatePattern(Long prdId, String pattern) {
        return formatDatePattern(prdId.intValue(), pattern);
    }

    public static String formatQuarterPattern(Long prdId) {
        return formatQuarterPattern(prdId.intValue());
    }

    public static Date add(Date fromDate, int num, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(type, num);
        return cal.getTime();
    }

    public static String dateToString(Date fromDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(fromDate);
    }

    public static String dateToStringQuater(Date fromDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(fromDate);
        return (fromDate.getMonth() / 3 + 1) + "/" + year;
    }

//    public static String getTimeValue(Date date, Integer timeType) {
//        SimpleDateFormat YYYY = new SimpleDateFormat(YYYY_PT);
//        SimpleDateFormat YYYYMM = new SimpleDateFormat(YYYYmm_PT);
//        String value = null;
//        if (TimeType.YEAR.value().equals(timeType)) {
//            value = YYYY.format(date);
//        } else if (TimeType.MONTH.value().equals(timeType)) {
//            value = YYYYMM.format(date);
//        } else if (TimeType.QUARTER.value().equals(timeType)) {
//            value = YYYY.format(date);
//            int quarter = date.getMonth() / 3 + 1;
//            value = value + "" + quarter;
//        }
//        return value;
//    }

//    public static String getTimeName(Integer prdId, Integer timeType) {
//        if (prdId == null) return StringUtils.EMPTY;
//        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
//        try {
//            Date value = sdf.parse(prdId.toString());
//            if (TimeType.YEAR.value().equals(timeType)) {
//                sdf = new SimpleDateFormat("yyyy");
//                return Translator.toLocale("label.timeType.year") + " " + sdf.format(value);
//            } else if (TimeType.MONTH.value().equals(timeType)) {
//                sdf = new SimpleDateFormat("MM/yyyy");
//                return Translator.toLocale("label.timeType.month") + " " + sdf.format(value);
//            } else if (TimeType.QUARTER.value().equals(timeType)) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(value);
//                int quarter = (calendar.get(Calendar.MONTH) / 3) + 1;
//                sdf = new SimpleDateFormat("yyyy");
//                return String.format("%s %d/%s", Translator.toLocale("label.timeType.quarter"), quarter, sdf.format(value));
//            }
//            sdf = new SimpleDateFormat("dd/MM/yyyy");
//            return Translator.toLocale("label.timeType.date") + " " + sdf.format(value);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return StringUtils.EMPTY;
//        }
//    }


//    public static String getExternalUrl(Environment env) {
//        String protocol = "http";
//        if (env.getProperty("server.ssl.key-store") != null) {
//            protocol = "https";
//        }
//        String serverPort = env.getProperty("server.port");
//        String contextPath = env.getProperty("server.servlet.context-path");
//        if (StringUtils.isBlank(contextPath)) {
//            contextPath = "/";
//        }
//        String hostAddress = "localhost";
//        try {
//            hostAddress = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException ignored) {
//            logger.error(ignored.getMessage(), ignored);
//        }
//        return String.format("%s://%s:%s%s", protocol, hostAddress, serverPort, contextPath);
//    }

    public static Instant toInstant(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof java.sql.Date) {
            return Instant.ofEpochMilli(((java.sql.Date) object).getTime());
        } else if (object instanceof Timestamp) {
            return Instant.ofEpochMilli(((Timestamp) object).getTime());
        }
        return null;
    }

//    public static Integer getPrePrdId(Integer prdId, Integer timeType) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
//        Date date = sdf.parse(prdId.toString());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        if (TimeType.YEAR.value().equals(timeType)) {
//            calendar.add(Calendar.YEAR, -1);
//        } else if (TimeType.MONTH.value().equals(timeType)) {
//            calendar.add(Calendar.MONTH, -1);
//        } else if (TimeType.QUARTER.value().equals(timeType)) {
//            calendar.add(Calendar.MONTH, -3);
//        }
//        return getDateInt(calendar.getTime(), Constants.DATE_PATTERN);
//    }

    public static boolean setField(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                logger.error(e.getMessage(), e);
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    public static String nameToCode(String str) {
        if (StringUtils.isEmpty(str)) return StringUtils.EMPTY;
        str = str.trim();
        str = str.toLowerCase();
        str = str.replaceAll(" +", " ");
        str = str.replaceAll(" ", "_");
        str = str.replace(Translator.toLocale("a.vnCharacter"), "a");
        str = str.replace(Translator.toLocale("e.vnCharacter"), "e");
        str = str.replace(Translator.toLocale("i.vnCharacter"), "i");
        str = str.replace(Translator.toLocale("o.vnCharacter"), "o");
        str = str.replace(Translator.toLocale("u.vnCharacter"), "u");
        str = str.replace(Translator.toLocale("y.vnCharacter"), "y");
        str = str.replace(Translator.toLocale("d.vnCharacter"), "d");
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        str = pattern.matcher(nfdNormalizedString).replaceAll("");
        return str;
    }

    public static String sha256(String str) {
        if (StringUtils.isEmpty(str)) return StringUtils.EMPTY;
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(str.getBytes());
            byte[] digestSha = sha.digest();

            return DatatypeConverter.printHexBinary(digestSha).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return StringUtils.EMPTY;
        }
    }

    public static String md5Encode(String str) {
        if (StringUtils.isEmpty(str)) return StringUtils.EMPTY;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return StringUtils.EMPTY;
        }
    }

//    public static Integer getTimeTypeFromPeriod(String period) {
//        int length = StringUtils.isNotEmpty(period) ? period.length() : 0;
//        switch (length) {
//            case 6:
//                return TimeType.MONTH.value();
//            case 5:
//                return TimeType.QUARTER.value();
//            case 4:
//                return TimeType.YEAR.value();
//            default:
//                return TimeType.FORTUITY.value();
//        }
//    }

    public static Integer getPrdIdFromPeriod(String period) {
        int length = StringUtils.isNotEmpty(period) ? period.length() : 0;
        int prdId;
        switch (length) {
            case 6:
                prdId = safeToInt(period + "01");
                break;
            case 5:
                prdId = safeToInt(period.substring(0, 4) + StringUtils.leftPad(((safeToInt(period.substring(4)) - 1) * 3 + 1) + "", 2, "0") + "01");
                break;
            case 4:
                prdId = safeToInt(period + "0101");
                break;
            default:
                prdId = safeToInt(period);
                break;
        }
        return prdId == 0 ? null : prdId;
    }

//    public static String getPeriod(Integer prdId, Integer timeType) {
//        String rs = StringUtils.EMPTY;
//        if (prdId == null) return rs;
//        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
//        Calendar calendar = Calendar.getInstance();
//        try {
//            Date prdDate = sdf.parse(prdId.toString());
//            calendar.setTime(prdDate);
//        } catch (ParseException e) {
//            logger.error(e.getMessage(), e);
//            return rs;
//        }
//        int month = (calendar.get(Calendar.MONTH) + 1);
//        if (TimeType.MONTH.value().equals(timeType)) {
//            return calendar.get(Calendar.YEAR) + "" + StringUtils.leftPad(month + "", 2, "0");
//        }
//        if (TimeType.QUARTER.value().equals(timeType)) {
//            int quarter = (month / 3) + 1;
//            return calendar.get(Calendar.YEAR) + "" + quarter;
//        }
//        if (TimeType.YEAR.value().equals(timeType)) {
//            return calendar.get(Calendar.YEAR) + "";
//        }
//        return prdId.toString();
//    }
}

