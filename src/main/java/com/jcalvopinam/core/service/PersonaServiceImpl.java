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

package com.jcalvopinam.core.service;

import com.jcalvopinam.core.converter.PersonDTOtoPersonConverter;
import com.jcalvopinam.core.converter.PersonToPersonDTOConverter;
import com.jcalvopinam.core.domain.Person;
import com.jcalvopinam.core.dto.PersonDTO;
import com.jcalvopinam.core.repository.PersonRepository;
import com.jcalvopinam.exception.PersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Juan Calvopina M. <juan.calvopina@gmail.com>
 */
@Service
public class PersonaServiceImpl implements PersonaService {

    private static final String PERSON_NOT_FOUND = "Person not found";

    private final PersonRepository personRepository;
    private final PersonDTOtoPersonConverter personDTOtoPersonConverter;
    private final PersonToPersonDTOConverter personToPersonDTOConverter;

    @Autowired
    public PersonaServiceImpl(final PersonRepository personRepository,
                              final PersonDTOtoPersonConverter personDTOtoPersonConverter,
                              final PersonToPersonDTOConverter personToPersonDTOConverter) {
        this.personRepository = personRepository;
        this.personDTOtoPersonConverter = personDTOtoPersonConverter;
        this.personToPersonDTOConverter = personToPersonDTOConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDTO save(final PersonDTO personDTO) {
        final Person person = personRepository.save(personDTOtoPersonConverter.convert(personDTO));
        return personToPersonDTOConverter.convert(person);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersonDTO> findAll() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                            .map(personToPersonDTOConverter::convert)
                            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDTO findById(final Long id) throws PersonException {
        return personToPersonDTOConverter.convert(this.getPersonById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDTO update(final PersonDTO personDTO, final Long id) throws PersonException {
        final Person personById = this.getPersonById(id);
        final Person person = personRepository.save(personDTOtoPersonConverter.convert(personDTO, personById));
        return personToPersonDTOConverter.convert(person);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Long id) throws PersonException {
        final Person personById = this.getPersonById(id);
        personRepository.delete(personById);
    }

    /**
     * It finds Person object by {@code id}
     *
     * @param id of the person to find.
     * @return Person object.
     * @throws PersonException If Person does not exist.
     */
    private Person getPersonById(final Long id) throws PersonException {
        return personRepository.findById(id)
                               .orElseThrow(() -> new PersonException(PERSON_NOT_FOUND));
    }

}
