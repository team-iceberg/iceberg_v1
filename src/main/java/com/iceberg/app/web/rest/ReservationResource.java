package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.DetailEmplacement;
import com.iceberg.app.domain.Reservation;
import com.iceberg.app.repository.DetailEmplacementRepository;
import com.iceberg.app.repository.ReservationRepository;
import com.iceberg.app.service.dto.ObjetReservationsDTO;
import com.iceberg.app.service.dto.ReservationDTO;
import com.iceberg.app.service.mapper.ReservationMapper;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST controller for managing Reservation.
 */
@RestController
@RequestMapping("/api")
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);

    private static final String ENTITY_NAME = "reservation";

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public ReservationResource(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    /**
     * POST  /reservations : Create a new reservation.
     *
     * @param reservationDTO the reservationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reservationDTO, or with status 400 (Bad Request) if the reservation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reservations")
    @Timed
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservationDTO);
        if (reservationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reservation cannot already have an ID")).body(null);
        }
        Reservation reservation = reservationMapper.reservationDTOToReservation(reservationDTO);
        DetailEmplacement detailEmpl = new DetailEmplacement();
        detailEmpl.setId(Long.valueOf((long)reservationDTO.getDetailEmplacementId()));
        reservation.setDetailEmplacement(detailEmpl);
        reservation = reservationRepository.save(reservation);
        ReservationDTO result = reservationMapper.reservationToReservationDTO(reservation);
        return ResponseEntity.created(new URI("/api/reservations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reservations : Updates an existing reservation.
     *
     * @param reservationDTO the reservationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservationDTO,
     * or with status 400 (Bad Request) if the reservationDTO is not valid,
     * or with status 500 (Internal Server Error) if the reservationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reservations")
    @Timed
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO) throws URISyntaxException {
        log.debug("REST request to update Reservation : {}", reservationDTO);
        if (reservationDTO.getId() == null) {
            return createReservation(reservationDTO);
        }
        Reservation reservation = reservationMapper.reservationDTOToReservation(reservationDTO);
        reservation = reservationRepository.save(reservation);
        ReservationDTO result = reservationMapper.reservationToReservationDTO(reservation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reservationDTO.getId().toString()))
            .body(result);
    }

    /**
     * PUT / reservations : Update resevation's list
     *
     * @param reservations
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/updateReservationList")
    @Timed
    public ResponseEntity<List<ReservationDTO>> updateReservationList(@RequestBody List<Reservation> reservations) throws URISyntaxException {
        log.debug("REST request to update Reservation : {}", reservations);
        List<ReservationDTO> resultat = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservation = reservationRepository.save(reservation);
            resultat.add(reservationMapper.reservationToReservationDTO(reservationRepository.save(reservation)));
        }

        return Optional.ofNullable(resultat)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /reservations : get all the reservations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reservations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/reservations")
    @Timed
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Reservations");
        Page<Reservation> page = reservationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservations");
        return new ResponseEntity<>(reservationMapper.reservationsToReservationDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /reservations/:id : get the "id" reservation.
     *
     * @param id the id of the reservationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reservations/{id}")
    @Timed
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        log.debug("REST request to get Reservation : {}", id);
        Reservation reservation = reservationRepository.findOne(id);
        ReservationDTO reservationDTO = reservationMapper.reservationToReservationDTO(reservation);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reservationDTO));
    }


    /**
     * GET  /reservations/:id : get the "id" reservation.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the reservationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reservationsEnCours")
    @Timed
    public ResponseEntity<List<ObjetReservationsDTO>> getReservationEnCours() {
        log.debug("REST request to get ReservationEnCours");

        List<Reservation> lReservations = reservationRepository.findAll();
        List<ReservationDTO> lResaDTO = reservationMapper.reservationsToReservationDTOs(lReservations);

        List<ObjetReservationsDTO> resultat = new ArrayList<>(50);

        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy/MM/dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss");
        //regrouper les réservations par date/objet/qui *
        Map<String, ObjetReservationsDTO > cache = new TreeMap<String, ObjetReservationsDTO>();
        Iterator<ReservationDTO> itResa = lResaDTO.iterator();
        while(itResa.hasNext()){
         	ReservationDTO itemResa = itResa.next();
         	if((itemResa.getQteResa().intValue() - itemResa.getQteRet().intValue()) > 0) {
	        	String key = itemResa.getDateReservation().format(dtf) + " - "+itemResa.getQui()+" - "+itemResa.getDetailEmplacement().getEmplacement().getObjet().getId();
	        	if(! cache.containsKey(key)){
	        		ObjetReservationsDTO unObjResa = new ObjetReservationsDTO();
	        		unObjResa.setQui(itemResa.getQui());
	        		unObjResa.setDateReservation(simpleDF.format(Date.from( itemResa.getDateReservation().toInstant())));
	        		unObjResa.setObjet(itemResa.getDetailEmplacement().getEmplacement().getObjet());
	        		List<Reservation> listResaEnCours = new ArrayList<Reservation>(20);

	        		listResaEnCours.add(reservationMapper.reservationDTOToReservation(itemResa));

	                DetailEmplacement tmpDE = new DetailEmplacement();
	                tmpDE.setId(itemResa.getDetailEmplacement().getId());
	                tmpDE.setQteEnCours(itemResa.getDetailEmplacement().getQteEnCours());
	                tmpDE.setEmplacement(itemResa.getDetailEmplacement().getEmplacement());
	                tmpDE.setValeurCaracteristique(itemResa.getDetailEmplacement().getValeurCaracteristique());


	        		unObjResa.setLesReservationsEnCours(listResaEnCours.stream()
	                    .map(f -> {
	                        if(f.getId().equals(itemResa.getId())){
	                            f.setDetailEmplacement(tmpDE);
	                        }
	                        return f;
	                    })
	                    .collect(Collectors.toList()));
	        		cache.put(key, unObjResa);
	        	}else {
	        		ObjetReservationsDTO unObjResa = cache.get(key);
	        		List<Reservation> listResaEnCours = unObjResa.getLesReservationsEnCours();
	        		listResaEnCours.add(reservationMapper.reservationDTOToReservation(itemResa));

	                DetailEmplacement tmpDE = new DetailEmplacement();
	                tmpDE.setId(itemResa.getDetailEmplacement().getId());
	                tmpDE.setQteEnCours(itemResa.getDetailEmplacement().getQteEnCours());
	                tmpDE.setValeurCaracteristique(itemResa.getDetailEmplacement().getValeurCaracteristique());

	                unObjResa.setLesReservationsEnCours(listResaEnCours.stream()
	                    .map(f -> {
	                        if(f.getId().equals(itemResa.getId())){
	                            f.setDetailEmplacement(tmpDE);
	                        }
	                        return f;
	                    })
	                    .collect(Collectors.toList()));
	        	}
         	}
        }

        Iterator<String> itKeyCache =  cache.keySet().iterator();
        while(itKeyCache.hasNext()){
        	String key = itKeyCache.next();
        	resultat.add(cache.get(key));
        }

        return Optional.ofNullable(resultat)
                .map(result -> new ResponseEntity<>(
                    result,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * DELETE  /reservations/:id : delete the "id" reservation.
     *
     * @param id the id of the reservationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reservations/{id}")
    @Timed
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        reservationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
