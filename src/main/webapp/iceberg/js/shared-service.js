angular.module('app', []).factory("SharedService", function () {

    var monObjet;

    var addData = function (data) {
        monObjet = data;
    };

    var getData = function () {
        return monObjet;
    };

    return {
        addData: addData,
        getData: getData
    }
});
