function prev() {
  var flag = true;
  $(".carousel .carouselItem").animate(
    { opacity: "0" },
    500,
    "swing",
    function () {
      if (flag) {
        $(".carousel")
          .prepend($(".carousel .carouselItem").last()[0])
          .find(".carouselItem")
          .animate({ opacity: "1" }, 500, "swing");
        flag = false;
      }
    }
  );
}
function next() {
  var flag = true;
  $(".carousel .carouselItem").animate(
    { opacity: "0" },
    500,
    "swing",
    function () {
      if (flag) {
        $(".carousel")
          .append($(".carousel .carouselItem").first()[0])
          .find(".carouselItem")
          .animate({ opacity: "1" }, 500, "swing");
        flag = false;
      }
    }
  );
}
