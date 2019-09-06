var elementos = $('a, h1, h2, h3, h4, p, span, input, h5, h6, b, label, select , button , body');
var coeficiente = 2;
var counter = 0;
var limite = 4;

// Reduce Font Size
$("#fontMenos").click(function () {
    $.fn.fontMenos();
});

$('#fontMenos').keypress(function (event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        $.fn.fontMenos();
    }
});

$.fn.fontMenos = () => {
    var elementos2 = $('a, h1, h2, h3, h4, p, span, input, h5, h6, b, label, select , button , body');
    counter -= coeficiente;

    if (counter >= limite * (-1)) {
        elementos2.each(function (index) {
            var k = parseInt($(this).css('font-size'));

            var redSize = (k - coeficiente); //disminuye 2px
            $(this).css('font-size', redSize);
        });
    }
    if (counter < limite * (-1)) {
        counter = limite * (-1);
    }
}


// Aument Font Size
$("#fontMas").click(function () {
    $.fn.fontMas();
});

$('#fontMas').keypress(function (event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        $.fn.fontMas();
    }
});

$.fn.fontMas = () => {
    var elementos3 = $('a, h1, h2, h3, h4, p, span, input, h5, h6, b, label, select , button , body');
    counter += coeficiente;

    if (counter <= limite) {
        elementos3.each(function (index) {
            var j = parseInt($(this).css('font-size'));

            var AuSize = (j + coeficiente); //aumenta 2px
            $(this).css('font-size', AuSize);
        });
    }
    if (counter > limite) {
        counter = limite;
    }
}

//Contrast button

var GetElem = $("div, section, ul.menu-dynamic, #people-list, #citizens-list, #builders-list, #employees-list, a, h1, h2, h3, h4, p, span, input, b, .button, .link-fondoenlinea a, .link-fondoenlinea a span");

$('html *:not(script, style, noscript)').each(function () {
    fondo = $(this).css("background-color");
    letra = $(this).css("color");
});

$('#contraste').click(function () {
    var iconos = ".links-accesibility a img, .resp-acces a img, .ms-srch-sb > .ms-srch-sb-searchLink, .page-utilities a img, .right-item-doc, .page-utilities .utility img, #gestion a.highlight-detail img, #content-related img, #quickLinks .container #highlights .hLights li.tab a img, #quickLinks .container #social ul.redesS li.dfwp-item a img, a.highlight-detail img, #Ayuda .container .right #channels img, .close-btn";
    var blanco = 'white';
    var negro = 'black';
    var menucolor = '#1e1e1e';

    $(this).toggleClass("contrast-active");
    $('body').toggleClass("alto_contraste");

    if ($("#contraste.contrast-active").length > 0) {

        $('html *:not(script, style, noscript)').each(
            function () {
                $(this).css({
                    "background-color": negro,
                    "color": blanco
                });
            });

        $(".link-fondoenlinea a img, a.dowload-app img")
            .css({
                "background": "transparent",
                "filter": "brightness(0%)"
            });

        $(iconos)
            .css("filter", "grayscale(100%) brightness(300%)");

        $("section")
            .not("#slider, .image-container-page")
            .each(
                function (index) {
                    $(
                        "#slider *, .image-container-page *, #seguros, #seguros *")
                        .css("background",
                            "transparent");
                    this.style.background = negro;
                });

        $("li.header-section, #toTop")
            .addClass("ico-contraste");

        $("#people-list, #citizens-list, #builders-list, #employees-list")
            .addClass("menu-contraste");

        $("a.Link")
            .addClass("link-contraste");

        $(".ms-rtestate-field ol > li, .lidentro li")
            .addClass("letter-list-contraste");

        $(".breadcrumb, .breadcrumb *")
            .css("background", "transparent");

        $(".button, .btn,  .link-fondoenlinea a, .link-fondoenlinea a span, a.dowload-app, a.dowload-app *:not(img)")
            .each(function (index) {
                this.style.background = blanco;
                $(this).css('color', negro);
            })

        $(".dColor, #trick_bg")
            .css("background", negro);

        $("#toTop")
            .css("background", "transparent");

        $('#logoGobiernoColombia')
            .attr("src", "/FNA-TemplateUI/fna/img/logo_gobierno_alto_contraste.png");

        $('.ion-social-apple, .ion-social-android')
            .css("color", negro);

        $('.cargando-gif')
            .attr("src", "assets/cargando-contrast.gif");

        $('#app-bar')
            .removeClass("bg-light")

        $('#clip-adjuntar')
            .attr("src", "assets/icons/clip_white.png");

        $("#burguer-menu")
            .addClass("navbar-dark");

        $("#toTop span.icon-fup")
            .addClass("contrast-toTop");

    } else {

        $('html *:not(script, style, noscript)').each(
            function () {
                $(this).css({
                    "background-color": "",
                    "color": ""
                });

            });

        $(".link-fondoenlinea a img, a.dowload-app img")
            .css({
                "background": "",
                "filter": ""
            });

        $(iconos)
            .css("filter", "");

        $("section").not("#slider, .image-container-page")
            .each(
                function (index) {
                    $("#slider .bannerTitle *")
                        .css("color", "");
                    this.style.background = "";
                });

        $("li.header-section, #toTop")
            .removeClass("ico-contraste");

        $("#people-list, #citizens-list, #builders-list, #employees-list")
            .removeClass("menu-contraste");

        $("a.Link")
            .removeClass("link-contraste")

        $(".ms-rtestate-field ol > li, .lidentro li")
            .removeClass("letter-list-contraste");

        $(".breadcrumb, .breadcrumb *")
            .css("background", "");

        $(".left * ")
            .css("background", "");

        $(".button, .btn, .link-fondoenlinea a, .link-fondoenlinea a img, .link-fondoenlinea a span")
            .each(function (index) {
                this.style.background = "";
                $(this).css('color', "");
            })

        $(".dColor, #trick_bg")
            .css("background", "");

        $("#toTop")
            .css("background", "");

        $('#logoGobiernoColombia')
            .attr("src", "/FNA-TemplateUI/fna/img/logo_gobierno.png");

        $('.ion-social-apple, .ion-social-android')
            .css("color", blanco);

        $('.cargando-gif')
            .attr("src", "assets/cargando.gif")

        $('#app-bar')
            .addClass("bg-light")

        $('#clip-adjuntar')
            .attr("src", "assets/icons/clip.png");

        $("#burguer-menu")
            .removeClass("navbar-dark");

        $("#toTop span.icon-fup")
            .removeClass("contrast-toTop");
    }
});

$('#contraste').keypress(function (event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        var iconos = ".links-accesibility a img, .resp-acces a img, .ms-srch-sb > .ms-srch-sb-searchLink, .page-utilities a img, .right-item-doc, .page-utilities .utility img, #gestion a.highlight-detail img, #content-related img, #quickLinks .container #highlights .hLights li.tab a img, #quickLinks .container #social ul.redesS li.dfwp-item a img, a.highlight-detail img, #Ayuda .container .right #channels img, .close-btn";
        var blanco = 'white';
        var negro = 'black';
        var menucolor = '#1e1e1e';

        $(this).toggleClass("contrast-active");
        $('body').toggleClass("alto_contraste");

        if ($("#contraste.contrast-active").length > 0) {

            $('html *:not(script, style, noscript)').each(
                function () {
                    $(this).css({
                        "background-color": negro,
                        "color": blanco
                    });
                });

            $(".link-fondoenlinea a img, a.dowload-app img")
                .css({
                    "background": "transparent",
                    "filter": "brightness(0%)"
                });

            $(iconos)
                .css("filter", "grayscale(100%) brightness(300%)");

            $("section")
                .not("#slider, .image-container-page")
                .each(
                    function (index) {
                        $(
                            "#slider *, .image-container-page *, #seguros, #seguros *")
                            .css("background",
                                "transparent");
                        this.style.background = negro;
                    });

            $("li.header-section, #toTop")
                .addClass("ico-contraste");

            $("#people-list, #citizens-list, #builders-list, #employees-list")
                .addClass("menu-contraste");

            $("a.Link")
                .addClass("link-contraste");

            $(".ms-rtestate-field ol > li, .lidentro li")
                .addClass("letter-list-contraste");

            $(".breadcrumb, .breadcrumb *")
                .css("background", "transparent");

            $(".button, .btn,  .link-fondoenlinea a, .link-fondoenlinea a span, a.dowload-app, a.dowload-app *:not(img)")
                .each(function (index) {
                    this.style.background = blanco;
                    $(this).css('color', negro);
                })

            $(".dColor, #trick_bg")
                .css("background", negro);

            $("#toTop")
                .css("background", "transparent");

            $('#logoGobiernoColombia')
                .attr("src", "/FNA-TemplateUI/fna/img/logo_gobierno_alto_contraste.png");

            $('.ion-social-apple, .ion-social-android')
                .css("color", negro);

            $('.cargando-gif')
                .attr("src", "assets/cargando-contrast.gif");

            $('#app-bar')
                .removeClass("bg-light")

            $('#clip-adjuntar')
                .attr("src", "assets/icons/clip_white.png");

            $("#toTop span.icon-fup")
                .addClass("contrast-toTop");
        } else {

            $('html *:not(script, style, noscript)').each(
                function () {
                    $(this).css({
                        "background-color": "",
                        "color": ""
                    });

                });

            $(".link-fondoenlinea a img, a.dowload-app img")
                .css({
                    "background": "",
                    "filter": ""
                });

            $(iconos)
                .css("filter", "");

            $("section").not("#slider, .image-container-page")
                .each(
                    function (index) {
                        $("#slider .bannerTitle *")
                            .css("color", "");
                        this.style.background = "";
                    });

            $("li.header-section, #toTop")
                .removeClass("ico-contraste");

            $("#people-list, #citizens-list, #builders-list, #employees-list")
                .removeClass("menu-contraste");

            $("a.Link")
                .removeClass("link-contraste")

            $(".ms-rtestate-field ol > li, .lidentro li")
                .removeClass("letter-list-contraste");

            $(".breadcrumb, .breadcrumb *")
                .css("background", "");

            $(".left * ")
                .css("background", "");

            $(".button, .btn, .link-fondoenlinea a, .link-fondoenlinea a img, .link-fondoenlinea a span")
                .each(function (index) {
                    this.style.background = "";
                    $(this).css('color', "");
                })

            $(".dColor, #trick_bg")
                .css("background", "");

            $("#toTop")
                .css("background", "");

            $('#logoGobiernoColombia')
                .attr("src", "/FNA-TemplateUI/fna/img/logo_gobierno.png");

            $('.ion-social-apple, .ion-social-android')
                .css("color", blanco);

            $('.cargando-gif')
                .attr("src", "assets/cargando.gif")

            $('#app-bar')
                .addClass("bg-light")

            $('#clip-adjuntar')
                .attr("src", "assets/icons/clip.png");

            $("#toTop span.icon-fup")
                .removeClass("contrast-toTop");
        }
    }
});