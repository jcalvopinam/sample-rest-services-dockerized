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

import com.jcalvopinam.core.dto.PersonDTO;
import com.jcalvopinam.core.exception.PersonException;

import java.util.List;

/**
 * @author Juan Calvopina M. <juan.calvopina@gmail.com>
 */
public interface PersonaService {

    /**
     * It allows you to save a Person object.
     *
     * @param personDTO object with personal information.
     * @return PersonDTO object.
     */
    PersonDTO save(PersonDTO personDTO);

    /**
     * It retrieves a list of Person object.
     *
     * @return a list of PersonDTO object.
     */
    List<PersonDTO> findAll();

    /**
     * It retrieves a Person object filtered by id.
     *
     * @return PersonDTO object.
     */
    PersonDTO findById(Long id) throws PersonException;

    /**
     * It allows you to update a Person object.
     *
     * @param personDTO object with personal information.
     * @param id        of the person to update.
     * @return PersonDTO object.
     */
    PersonDTO update(PersonDTO personDTO, Long id) throws PersonException;

    /**
     * It allows you to delete a Person object.
     *
     * @param id of the person to delete.
     */
    void delete(Long id) throws PersonException;

}
