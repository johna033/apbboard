/**
 * Created by homer on 12.09.14.
 */

var getPageNumber = function(size, offset){
    return size == 0 ? 1 : offset / size + 1;
};

var getBoundsFromPageNumber = function(pageNumber, itemsPerPage){
    return {offset:(pageNumber - 1)*itemsPerPage, size:itemsPerPage};
};
