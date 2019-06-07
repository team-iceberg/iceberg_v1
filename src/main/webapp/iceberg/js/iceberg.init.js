//app
(function () {
    'use strict';

    var app = angular.module("app", ['ngMaterial', 'ngRoute', 'ui.grid', 'ui.grid.edit', 'ngMessages']);
    app.config(function ($routeProvider) {
        var vm = this;
        $routeProvider
            .when("/", {
                templateUrl: "/iceberg/content.search.html"
            })
            .when("/detail/:id", {
                templateUrl: "/iceberg/content.detail.html",
                controller: "appControllerDetail as vm"
            })
            .when("/retour", {
                templateUrl: "/iceberg/content.search.html"
            })
            .when("/creer", {
                templateUrl: "/iceberg/content.create.html",
                controller: "appControllerCreate as vm"
            })
            .when("/resaEnCours", {
                templateUrl: "/iceberg/content.resaEnCours.html",
                controller: "appControllerResaEnCours as vm"
            })
            .when("/reserver", {
                templateUrl: "/iceberg/content.reserve.html",
                controller: "appControllerReserve as vm"
            })
            .when("/booking-detail", {
                templateUrl: "/iceberg/content.return.html",
                controller: "appControllerReturn as vm"
            });
    });
})();
