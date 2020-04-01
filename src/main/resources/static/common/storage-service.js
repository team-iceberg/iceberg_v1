(function () {
    /**
     * Service de CRUD pour la fonctionnalité HTLM5 du localstorage
     */
    angular.module('app').factory('$localstorage', localStorageService);

    function localStorageService() {

        var ICEBERG_USER = "ICEBERG_USER";
        var ICEBERG_TAILLES = "ICEBERG_TAILLES";
        var ICEBERG_TYPES = "ICEBERG_TYPES";
        var ICEBERG_COULEURS = "ICEBERG_COULEURS";
        var ICEBERG_EMPLACEMENTS = "ICEBERG_EMPLACEMENTS";
        var ICEBERG_ADD_NEW_BOX = "ICEBERG_ADD_NEW_BOX";
        var ICEBERG_OBJECT_BOOKED = "ICEBERG_OBJECT_BOOKED";
        var ICEBERG_OBJECT_MODIFY = "ICEBERG_OBJECT_MODIFY";

        /**
         * Retourne une entrée en fonction de sa clé.
         *
         * @param key la clé à chercher
         */
        function get(key) {
            return localStorage.getItem(key);
        }

        /**
         * Sauvegarde une entrée
         *
         * @param key la clé
         * @param data la donnée
         */
        function save(key, data) {
            localStorage.setItem(key, JSON.stringify(data));
        }

        /**
         * Supprime une entrée en fonction de sa clé
         *
         * @param key la clé
         */
        function remove(key) {
            localStorage.removeItem(key);
        }

        function saveUser(User){
            save(ICEBERG_USER, User);
        }

        function getUser() {
            return angular.fromJson(get(ICEBERG_USER));
        }

        function removeUser(){
            remove(ICEBERG_USER);
        }

        function saveTailles(tailles){
            save(ICEBERG_TAILLES, tailles);
        }

        function getTailles() {
            return angular.fromJson(get(ICEBERG_TAILLES));
        }

        function removeTailles(){
            remove(ICEBERG_TAILLES);
        }

        function saveTypes(types){
            save(ICEBERG_TYPES, types);
        }

        function getTypes() {
            return angular.fromJson(get(ICEBERG_TYPES));
        }

        function removeTypes(){
            remove(ICEBERG_TYPES);
        }

        function saveCouleurs(couleurs){
            save(ICEBERG_COULEURS, couleurs);
        }

        function getCouleurs() {
            return angular.fromJson(get(ICEBERG_COULEURS));
        }

        function removeCouleurs(){
            remove(ICEBERG_COULEURS);
        }

        function saveEmplacements(couleurs){
            save(ICEBERG_EMPLACEMENTS, couleurs);
        }

        function getEmplacements() {
            return angular.fromJson(get(ICEBERG_EMPLACEMENTS));
        }

        function removeEmplacements(){
            remove(ICEBERG_EMPLACEMENTS);
        }

        function saveCanAddNewBox(canAddNewBox){
            save(ICEBERG_ADD_NEW_BOX, canAddNewBox);
        }

        function getCanAddNewBox() {
            return angular.fromJson(get(ICEBERG_ADD_NEW_BOX));
        }

        function removeCanAddNewBox(){
            remove(ICEBERG_ADD_NEW_BOX);
        }

        function saveObjectBooked(canAddNewBox){
            save(ICEBERG_OBJECT_BOOKED, canAddNewBox);
        }

        function getObjectBooked() {
            return angular.fromJson(get(ICEBERG_OBJECT_BOOKED));
        }

        function removeObjectBooked(){
            remove(ICEBERG_OBJECT_BOOKED);
        }

        function saveObjectModify(object){
            save(ICEBERG_OBJECT_MODIFY, object);
        }

        function getObjectModify() {
            return angular.fromJson(get(ICEBERG_OBJECT_MODIFY));
        }

        function removeObjectModify(){
            remove(ICEBERG_OBJECT_MODIFY);
        }


        function clearStorage() {
            removeUser();
            removeTailles();
            removeTypes();
            removeCouleurs();
            removeEmplacements();
            removeCanAddNewBox();
            removeObjectBooked();
        }

        /**
         *  Retour de factory
         */
        return {
            save: save,
            get: get,
            remove: remove,
            saveUser: saveUser,
            getUser: getUser,
            removeUser: removeUser,
            saveTailles: saveTailles,
            getTailles: getTailles,
            removeTailles: removeTailles,
            saveTypes: saveTypes,
            getTypes: getTypes,
            removeTypes: removeTypes,
            saveCouleurs: saveCouleurs,
            getCouleurs: getCouleurs,
            removeCouleurs: removeCouleurs,
            saveEmplacements: saveEmplacements,
            getEmplacements: getEmplacements,
            removeEmplacements: removeEmplacements,
            saveCanAddNewBox: saveCanAddNewBox,
            getCanAddNewBox: getCanAddNewBox,
            removeCanAddNewBox: removeCanAddNewBox,
            clearStorage : clearStorage,
            saveObjectBooked: saveObjectBooked,
            getObjectBooked: getObjectBooked,
            removeObjectBooked : removeObjectBooked,
            saveObjectModify: saveObjectModify,
            getObjectModify: getObjectModify,
            removeObjectModify : removeObjectModify
        };
    }

})();


