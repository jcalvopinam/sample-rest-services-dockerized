/*
 * MIT License
 *
 * Copyright (c) 2019. JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jcalvopinam.core.converter;

import com.jcalvopinam.core.domain.Person;
import com.jcalvopinam.core.dto.PersonDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Juan Calvopina M. <juan.calvopina@gmail.com>
 */
@Component
public class PersonDTOtoPersonConverter implements Converter<PersonDTO, Person> {

    private static final String[] IGNORED_PROPERTIES = {"id"};

    @Override
    public Person convert(final PersonDTO personDTO) {
        final Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        return person;
    }

    public Person convert(final PersonDTO personDTO, final Person person) {
        BeanUtils.copyProperties(personDTO, person, IGNORED_PROPERTIES);
        return person;
    }

}