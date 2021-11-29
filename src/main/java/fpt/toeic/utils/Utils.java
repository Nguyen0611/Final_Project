package fpt.toeic.utils;

import fpt.toeic.service.dto.UsersDTO;
import io.github.jhipster.service.filter.*;
import fpt.toeic.config.Constants;
import fpt.toeic.service.dto.ChangePassDTO;
import fpt.toeic.service.dto.UsersDTO;
import fpt.toeic.utils.annotations.Ignore;
import fpt.toeic.utils.annotations.UpperCaseValue;
import fpt.toeic.web.rest.errors.MessageConsumeException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private Utils() {
    }

    public static String getIp(HttpServletRequest request) {
//         doan lay ip may khach ( đang lấy sai địa chỉ ip local )
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                try {
                    remoteAddr = Inet4Address.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    remoteAddr = request.getRemoteAddr();
                }
//                remoteAddr=  Inet4Address.getLocalHost().getHostAddress();
//                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public static String getNameClient(){
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (UnknownHostException ex) {
            return null;
        }
    }

    private static Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }
        return result;
    }

    public static String formatCurrency(BigDecimal value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(10);
        format.setRoundingMode(RoundingMode.HALF_DOWN);
        return format.format(value);
    }

    public static String formatCurrencyToBigDecimal(String value) {
        return value.replace("đ", "").replace(".", "").trim();
    }


    public static void checkLogin(UsersDTO obj) {
        if (ValidateUtils.isEmpty(obj.getName())) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_USER_REQUIRED));
        }
        if (ValidateUtils.isEmpty(obj.getPass())) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_REQUIRED));
        }
        if (ValidateUtils.checkLength(obj.getPass(), 0, 6)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_MIN));
        }
        if (ValidateUtils.checkLengthMax(obj.getPass(), 60)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_MAX));
        }
    }

    public static void checkRestPass(ChangePassDTO obj) {
        if (StringUtils.isNotEmpty(obj.getUserName())) {
            if (ValidateUtils.isEmpty(obj.getOldPass())) {
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_OLL_REQUIRED));
            }

            if (ValidateUtils.checkLength(obj.getOldPass(), 0, 6)) {
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_OLL_MIN));
            }

            if (ValidateUtils.checkLengthMax(obj.getOldPass(), 60)) {
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_OLL_MAX));
            }
        }

        if (ValidateUtils.isEmpty(obj.getNewPass())) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_REQUIRED));
        }
        if (ValidateUtils.checkLength(obj.getNewPass(), 0, 6)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_MIN));
        }
        if (ValidateUtils.checkLengthMax(obj.getNewPass(), 60)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_MAX));
        }

        if (ValidateUtils.isEmpty(obj.getComPass())) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_COM_REQUIRED));
        }

        if (ValidateUtils.checkLength(obj.getComPass(), 0, 6)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_COM_MIN));
        }

        if (ValidateUtils.checkLengthMax(obj.getComPass(), 60)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_COM_MAX));
        }
    }

    public static void checkInsert(UsersDTO usersDTO) {
        if (usersDTO.getName() == null) {
            throw new ServiceException("Tên người dùng không được để trống");
        }
        if (usersDTO.getFullName() == null) {
            throw new ServiceException("Tên đầy đủ người dùng không được để trống");
        }
        if (usersDTO.getMail() == null) {
            throw new ServiceException("Tài khoản Email không được để trống");
        }
        if (usersDTO.getPass() == null) {
            throw new ServiceException("Mật khẩu không được để trống");
        }
        if (usersDTO.getDateOfBirth().equals(null)) {
            throw new ServiceException("Ngày sinh không được để trống");
        }
    }



    private static final String THAMSO = "Tham số: ";
    private static final String TIMETEMP_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final String DATE_FORMAT = "yyyyMMdd";

    public static <T> T mapData(Class<T> clazz, String formatDate, JSONObject... args) throws IllegalAccessException,
        ParseException, InstantiationException {
        List<String> errors = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        Field[] fields = clazz.getDeclaredFields();
        T t = clazz.newInstance();
        for (Field field : fields) {
            String key = field.getName();
            int size = field.getAnnotation(Size.class) != null ? field.getAnnotation(Size.class).max() : 99999;
            boolean requied = field.getAnnotation(NotNull.class) == null;
            boolean isUpper = field.getAnnotation(UpperCaseValue.class) != null && field.getAnnotation(UpperCaseValue.class).upper();

            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            // custom map entity and json
            String keyinJsonObj = StringUtils.isBlank(MappingColumn.getKeyMap(key)) ? key
                : MappingColumn.getKeyMap(key);
            try {
                for (JSONObject jsonObject : args) {
                    if (ObjectUtils.isEmpty(jsonObject)) {
                        continue;
                    }
                    if (jsonObject.isNull(keyinJsonObj) || ObjectUtils.isEmpty(jsonObject.get(keyinJsonObj)) || jsonObject.get(keyinJsonObj).equals("null")) {
                        if (!requied) {
                            errors.add("Key " + keyinJsonObj + " bắt buộc phải có dữ liệu");
                            continue;
                        } else {
                            field.set(t, null);
                            break;
                        }
                    }

                    if (jsonObject.get(keyinJsonObj).toString().length() > size || jsonObject.get(keyinJsonObj)
                        .toString().length() > field.getName().length()) {
                        errors.add("Key " + keyinJsonObj + " vượt quá độ rộng cho phép = " + size);
                        continue;
                    }

                    if (fieldType.equals(String.class)) {
                        if (isUpper) {
                            field.set(t, String.valueOf(jsonObject.get(keyinJsonObj)).toUpperCase());
                        } else {
                            field.set(t, String.valueOf(jsonObject.get(keyinJsonObj)));
                        }

                        break;
                    }
                    if (fieldType.equals(Integer.class) ) {
                        field.set(t, jsonObject.getInt(keyinJsonObj));
                        break;
                    }
                    if (fieldType.equals(Long.class)) {
                        field.set(t, jsonObject.getLong(keyinJsonObj));
                        break;
                    }
                    if (fieldType.equals(Double.class)) {
                        field.set(t, jsonObject.getDouble(keyinJsonObj));
                        break;
                    }
                    if (fieldType.equals(Boolean.class)) {
                        field.set(t, jsonObject.getBoolean(keyinJsonObj));
                        break;
                    }
                    if (fieldType.equals(Short.class)) {
                        field.set(t, Short.valueOf(jsonObject.getString(keyinJsonObj)));
                        break;
                    }
                    if (fieldType.equals(BigDecimal.class)) {
                        field.set(t, BigDecimal.valueOf(jsonObject.getDouble(keyinJsonObj)));
                        break;
                    }
                    if (fieldType.equals(Date.class)) {
                        Date date = simpleDateFormat.parse(jsonObject.getString(keyinJsonObj));
                        field.set(t, date);
                        break;
                    }
                    if (fieldType.equals(ZonedDateTime.class)) {
                        ZonedDateTime date = FunctionCommon.getDateTime(jsonObject.getString(keyinJsonObj), formatDate);
                        field.set(t, date);
                        break;
                    }
                    if (fieldType.equals(LocalDate.class)) {
                        LocalDate date = DateUtil.getDate(jsonObject.getString(keyinJsonObj), formatDate);
                        field.set(t, date);
                        break;
                    }
                }
            } catch (JSONException e) {
                errors.add("Key: " + keyinJsonObj + " phai la kieu " + fieldType.getSimpleName());
            } catch (DateTimeParseException e) {
                errors.add("Key: " + keyinJsonObj + " phai dung dinh dang ngay thang ");
            }
        }
//        if (!errors.isEmpty()) {
//            throw new MessageConsumeException(errors);
//        }
        return t;
    }



    public static boolean isStringInt(String s)
    {
        try
        {
            int a=Integer.parseInt(s);
            return a > 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static <T> JSONArray convertListEntityToJSONArray(List<T> listData) {
        JSONArray jarResponse = new JSONArray();
        try {
            for (T object : listData) {
                jarResponse.put(convertEntityToJSONObject(object));
            }
        } catch (Exception e) {
            return new JSONArray();
        }
        return jarResponse;
    }

    public static JSONObject convertEntityToJSONObject(Object object) {
        JSONObject jObj = new JSONObject();

        try {

            Field changeMap = jObj.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(jObj, new LinkedHashMap<>());
            changeMap.setAccessible(false);

            Class<? extends Object> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();

                // Ignore field
                if (field.isAnnotationPresent(Ignore.class)) {
                    continue;
                }

                if (fieldType.equals(ZonedDateTime.class) && field.get(object) != null) {
                    ZonedDateTime fieldDate = ZonedDateTime.parse(field.get(object).toString());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    jObj.put(fieldName, formatter.format(fieldDate));
                } else if (fieldType.equals(LocalDate.class) && field.get(object) != null) {
                    LocalDate fieldDate = LocalDate.parse(field.get(object).toString());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                    jObj.put(fieldName, formatter.format(fieldDate));
                } else if (field.isAnnotationPresent(ManyToOne.class)) {
                    if (object != null && field.get(object) != null) {
                        Class<? extends Object> clazzChild = field.get(object).getClass();
                        Object objChild = field.get(object);
                        Field[] fieldsChild = clazzChild.getDeclaredFields();
                        String nameRefer = field.getAnnotation(JoinColumn.class).referencedColumnName();
                        for (Field fieldChild : fieldsChild) {
                            fieldChild.setAccessible(true);
                            String fieldNameChild = fieldChild.getAnnotation(Column.class) != null
                                ? fieldChild.getAnnotation(Column.class).name()
                                : "";
                            if (fieldNameChild.toUpperCase().equalsIgnoreCase(nameRefer.toUpperCase())) {
                                jObj.put(fieldName, String.valueOf(fieldChild.get(objChild)));
                            }
                        }
                    }


                } else if (field.isAnnotationPresent(OneToOne.class)) {
                    if (object != null && field.get(object) != null) {
                        Class<? extends Object> clazzChild = field.get(object).getClass();
                        Object objChild = field.get(object);
                        Field[] fieldsChild = clazzChild.getDeclaredFields();
                        String nameRefer = field.getAnnotation(JoinColumn.class).referencedColumnName();
                        for (Field fieldChild : fieldsChild) {
                            fieldChild.setAccessible(true);
                            String fieldNameChild = fieldChild.getAnnotation(Column.class) != null
                                ? fieldChild.getAnnotation(Column.class).name()
                                : "";
                            if (fieldNameChild.toUpperCase().equalsIgnoreCase(nameRefer.toUpperCase())) {
                                jObj.put(fieldName, String.valueOf(fieldChild.get(objChild)));
                            }
                        }
                    }


                } else if (field.isAnnotationPresent(OneToMany.class)) {
                    continue;
                } else if (field.isAnnotationPresent(ManyToMany.class)) {
                    continue;
                } else {
                    jObj.put(fieldName, field.get(object) != null ? field.get(object) : "");
                }
            }

        } catch (Exception e) {
            return new JSONObject();
        }
        return jObj;
    }

    public static <T> T mappingCriteria(Class<T> clazz, JSONObject jParam) throws IllegalAccessException,
        InstantiationException, MessageConsumeException {
        List<String> errors = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        T t = clazz.newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            String key = field.getName();
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            String keyInJsonObj = StringUtils.isBlank(MappingColumn.getKeyMap(key)) ? key : MappingColumn.getKeyMap(key);
            try {
                if (ObjectUtils.isEmpty(jParam)) {
                    continue;
                }
                Iterator<String> itr = jParam.keys();
                String keyParam = null;
                while (itr.hasNext()) {
                    keyParam = itr.next();
                    if (!key.equals(keyParam)) {
                        continue;
                    }

                    if (fieldType.equals(StringFilter.class) && StringUtils.isNotBlank(jParam.getString(keyParam))) {
                        StringFilter sFilter = new StringFilter();
                        sFilter.setContains(jParam.getString(keyParam));
                        field.set(t, sFilter);
                        break;
                    }
                    if (fieldType.equals(IntegerFilter.class) && StringUtils.isNotBlank(jParam.getString(keyParam))) {
                        IntegerFilter iFilter = new IntegerFilter();
                        iFilter.setEquals(jParam.getInt(keyParam));
                        field.set(t, iFilter);
                        break;
                    }
                    if (fieldType.equals(LongFilter.class)) {
                        LongFilter lFilter = new LongFilter();
                        lFilter.setEquals(jParam.getLong(keyParam));
                        field.set(t, lFilter);
                        break;
                    }
                    if (fieldType.equals(DoubleFilter.class)) {
                        DoubleFilter dFilter = new DoubleFilter();
                        dFilter.setEquals(jParam.getDouble(keyParam));
                        field.set(t, dFilter);
                        break;
                    }
                    if (fieldType.equals(BooleanFilter.class)) {
                        BooleanFilter bFilter = new BooleanFilter();
                        bFilter.setEquals(jParam.getBoolean(keyParam));
                        field.set(t, bFilter);
                        break;
                    }
                    if (fieldType.equals(ShortFilter.class)) {
                        ShortFilter stFilter = new ShortFilter();
                        stFilter.setEquals(Short.valueOf(jParam.getString(keyParam)));
                        field.set(t, stFilter);
                        break;
                    }
                    if (fieldType.equals(BigDecimal.class)) {
                        field.set(t, BigDecimal.valueOf(jParam.getDouble(keyParam)));
                        break;
                    }
                    if (fieldType.equals(ZonedDateTimeFilter.class)) {
                        ZonedDateTimeFilter sDateFilter = new ZonedDateTimeFilter();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate ld = LocalDate.parse( jParam.getString(keyParam) ,formatter) ;
                        ZoneId z = ZoneId.systemDefault() ;
                        ZonedDateTime start = ld.atStartOfDay( z ) ;
                        sDateFilter.setLessThan(start.plusDays(1L));
                        sDateFilter.setGreaterThan(start);
                        field.set(t, sDateFilter);
                        break;
                    }
                    if (fieldType.equals(LocalDateFilter.class)) {
                        LocalDateFilter lDateFilter = new LocalDateFilter();
                        LocalDate date = DateUtil.getDate(jParam.getString(keyParam), TIMETEMP_FORMAT);
                        lDateFilter.setLessThanOrEqual(date);
                        field.set(t, lDateFilter);
                        break;
                    }
                }

            } catch (JSONException e) {
                errors.add(THAMSO + keyInJsonObj + " phải là kiểu " + fieldType.getSimpleName());
            } catch (DateTimeParseException e) {
                errors.add(THAMSO + keyInJsonObj + " phải đúng định dạng ngày tháng ");
            }
        }

        if (!errors.isEmpty()) {
            throw new MessageConsumeException(errors);
        }
        return t;
    }

    public static JSONObject updateJsontoCamelCase(JSONObject obj, boolean isLower) {
        JSONObject jsonObject = new JSONObject();
        // get the keys of json object
        Iterator<String> itr = obj.keys();
        String key = null;
        while (itr.hasNext()) {
            key = itr.next();
            // if the key is a string, then update the value
            if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
                // put new value
//                String newValue = isLower ? CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key)
//                    : CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, key);
                jsonObject.put(key, obj.get(key));
            }

            // if it's jsonobject
            if (obj.optJSONObject(key) != null) {
                JSONObject jsonObject1 =obj.getJSONObject(key);
                jsonObject.put( key, jsonObject1);
            }

            // if it's jsonarray
            if (obj.optJSONArray(key) != null) {
                JSONArray jArray = obj.getJSONArray(key);
                JSONArray jarr = new JSONArray();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject1 = jArray.getJSONObject(i);
                    jarr.put(jsonObject1);
                }
                jsonObject.put( key, jarr);
            }
        }
        return jsonObject;
    }

    public static JSONObject updateJsontoSnakeCase(JSONObject obj, boolean isLower) {
        JSONObject jsonObject = new JSONObject();
        // get the keys of json object
        Iterator<String> itr = obj.keys();
        String key = null;
        while (itr.hasNext()) {
            key = itr.next();
            Object intervention = (!obj.isNull(key) && obj.get(key) != null) ? obj.get(key) : new Object();

            if (intervention instanceof JSONObject) {
                JSONObject jsonObject1 = updateJsontoSnakeCase(obj.getJSONObject(key), isLower);
                jsonObject.put(camelCaseToSnakecase(key, isLower), jsonObject1);
            } else if (intervention instanceof JSONArray) {
                JSONArray jArray = obj.getJSONArray(key);
                JSONArray jarr = new JSONArray();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject1 = updateJsontoSnakeCase(jArray.getJSONObject(i), isLower);
                    jarr.put(jsonObject1);
                }
                jsonObject.put(camelCaseToSnakecase(key, isLower), jarr);

            } else if (intervention instanceof String) {
                String newValue = camelCaseToSnakecase(key, isLower);
                jsonObject.put(newValue, obj.get(key));
            }
        }
        return jsonObject;
    }

    private static String camelCaseToSnakecase(String s, boolean isLower) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(s);

        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "_" + m.group().toLowerCase());
        }
        m.appendTail(sb);
        return isLower ? sb.toString().toLowerCase() : sb.toString().toUpperCase();
    }

    public static Pageable getPage(JSONObject reqParamObj,String sort) {
        if (!reqParamObj.isNull("page") && !ObjectUtils.isEmpty(reqParamObj.get("page")) &&
            !reqParamObj.isNull("page_size") && !ObjectUtils.isEmpty(reqParamObj.get("page_size"))
        ) {
            return PageRequest.of(reqParamObj.getInt("page"), reqParamObj.getInt("page_size"), Sort.by(sort));
        } else {
            return PageRequest.of(0, 20, Sort.by(sort));
        }
    }

    public static String getStatusOk( String message, JSONObject arrayResult) {
        JSONObject result = new JSONObject();
        result.put(Constants.CODE, 200);
        result.put(Constants.MESSAGE, message);
        result.put(Constants.DATA, arrayResult);
        return result.toString();
    }

    public static String getStatusBadRequest(String message) {
        try {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
        }
        JSONObject result = new JSONObject();
        result.put(Constants.CODE, 400);
        result.put(Constants.MESSAGE, message);
        return result.toString();
    }

    public static String toUpcase(String message) {
        char[] charArray = message.toCharArray();
        boolean foundSpace = true;
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                if (foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                foundSpace = true;
            }
        }
        return String.valueOf(charArray);
    }

}
