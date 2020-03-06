package com.benjamin.manga.model;

import com.benjamin.manga.constant.Pattern;
import com.benjamin.manga.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
public abstract class AbstractModel {

    @Id
    protected String id;

    @Field("reg_time")
    protected Long regTime;

    public AbstractModel() {
        long currentTime = DateTimeUtil.getCurrentTime();
        regTime = DateTimeUtil.convertDate(currentTime, Pattern.YYYYMMDDHHMMSS);
    }
}
