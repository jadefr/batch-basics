package com.jade.batchbasics.configuration;

import com.jade.batchbasics.model.City;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class CityFieldSetMapper implements FieldSetMapper<City> {

    @Override
    public City mapFieldSet(FieldSet fieldSet) throws BindException {
        final City city = new City();

//        city.setId(Long.parseLong(fieldSet.readString(0)));
        city.setId(fieldSet.readString(0));
        city.setUf(fieldSet.readString(1));
        city.setName(fieldSet.readString(2));
        city.setCapital(fieldSet.readString(3));
        city.setLon(fieldSet.readString(4));
        city.setLat(fieldSet.readString(5));
        city.setNo_accents(fieldSet.readString(6));
        city.setAlternative_names(fieldSet.readString(7));
        city.setMicroregion(fieldSet.readString(8));
        city.setMesoregion(fieldSet.readString(9));

        return city;
    }
}
