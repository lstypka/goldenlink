'use strict';

angular.module('clientApp').directive("sortTiles", function (expression, compiledElement) {
    // add my:sortable-index to children so we know the index in the model
    compiledElement.children().attr("sortTiles-index", "{{$index}}");

    return function (linkElement) {
        var scope = this;

        linkElement.sortable({
            placeholder: "placeholder",
            opacity: 0.8,
            axis: "x",
            update: function (event, ui) {
                // get model
                var model = scope.$apply(expression);
                window.console.log("MODEL", model);
                // remember its length
                var modelLength = model.length;
                // rember html nodes
                var items = [];

                // loop through items in new order
                linkElement.children().each(function (index) {
                    var item = $(this);

                    // get old item index
                    var oldIndex = parseInt(item.attr("sortTiles-index"), 10);

                    // add item to the end of model
                    model.push(model[oldIndex]);

                    if (item.attr("sortTiles-index")) {
                        // items in original order to restore dom
                        items[oldIndex] = item;
                        // and remove item from dom
                        item.detach();
                    }
                });

                model.splice(0, modelLength);

                // restore original dom order, so angular does not get confused
                linkElement.append.apply(linkElement, items);

                // notify angular of the change
                scope.$digest();
            }
        });
    };
});
