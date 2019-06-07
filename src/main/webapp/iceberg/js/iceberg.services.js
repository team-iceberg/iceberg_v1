//service
(function () {
    'use strict';

    angular.module("app").service('appService', ['$http', appService]);

    function appService($http) {
        var urlbase = '/api/';

        this.getAllCouleurs = function () {
            return $http.get(urlbase + 'param-couleurs/');
        };

        this.getAllTypes = function () {
            return $http.get(urlbase + 'param-types/');
        };

        this.getAllTailles = function () {
            return $http.get(urlbase + 'param-tailles/');
        };

        this.getResaEnCours = function () {
            return $http.get(urlbase + 'reservationsEnCours/');
        };

        this.getAllEmplacement = function () {
            return $http.get(urlbase + 'param-empls/')
        };

        this.getAllMono = function () {
            return $http.get(urlbase + 'usersMono/')
        };

        this.getObjet = function (userId, type, couleur, taille, empl) {
            return $http.get(urlbase + 'objetsByCaracteristiques/2/' + type + '/' + couleur + '/' + taille + '/' + empl);
        };

        this.getMyObjet = function (idObjet) {
            return $http.get(urlbase + '/objets/' + idObjet);
        };

        this.getMyDetailEmplacement = function (idDetailEmpl) {
            return $http.get(urlbase + '/detail-emplacements/' + idDetailEmpl);
        };

        this.majDetailEmplacement = function (detailEmpl) {
            return $http.put(urlbase + 'detail-emplacements/', detailEmpl);
        };

        this.deleteObjet = function (idObjet) {
            return $http({
                method: 'DELETE',
                url: urlbase + 'objets/' + idObjet,
                headers: {
                    'Content-type': 'application/json;charset=utf-8'
                }
            });
        };

        this.insertReservations = function (resa) {
            return $http.post(urlbase + 'reservations/', resa);
        };

        this.insertObject = function (object) {
            return $http.post(urlbase + 'objets', object);
        };

        this.insertCaracteristique = function (caract) {
            return $http.post(urlbase + 'objet-caracteristiques/', caract);
        };

        this.insertEmplacement = function (emplt) {
            return $http.post(urlbase + 'emplacements/', emplt);
        };

        this.insertDetailEmplacement = function (detailEmplt) {
            return $http.post(urlbase + 'detail-emplacements/', detailEmplt);
        };

        this.getAccount = function () {
            return $http.get(urlbase + 'account/')
        };

        this.createParamEmpl = function (object) {
            return $http.post(urlbase + 'param-empls', object);
        };

        this.updateReservations = function (object) {
            return $http.put(urlbase + 'reservations/', object);
        };

        this.updateReservationsList = function (reservationsList) {
            return $http.put(urlbase + 'updateReservationList/', reservationsList);
        };

        this.updateQtyDetailObject = function (detailEmplList) {
            return $http.put(urlbase + 'update-qty-detail-emplacements/', detailEmplList);
        };

        this.updateObject = function (object) {
            return $http.put(urlbase + 'objets', object);
        };

        this.updateCaracteristique = function (caract) {
            return $http.put(urlbase + 'objet-caracteristiques/', caract);
        };

        this.removeCharacteristic = function (idCharact) {
            return $http.delete(urlbase + 'objet-caracteristiques/' + idCharact);
        };

        this.removeDetailBox = function (idDetailBox) {
            return $http.delete(urlbase + 'detail-emplacements/' + idDetailBox);
        };

        this.updateEmplacement = function (emplt) {
            return $http.put(urlbase + 'emplacements/', emplt);
        };
    }
})();
