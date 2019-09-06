$(document).ready(function () {


  /*resizeIframe();
  // Para establecer height dinámico de la etiqueta iframe
  // Solo va a funcionar si la página está dentro del mismo dominio que el iframe (header y footer)
  $('iframe').on("load", function() {
  $('iframe').height($('iframe').contents().height());
  alert($('iframe').contents().height());
  });
  function resizeIframe() {
    if ($('iframe').contents().find('html').height() > 100) {
      $('iframe').height(($('iframe').contents().find('html').height()) + 'px')
      console.log($('iframe').height(($('iframe').contents().find('html').height()) + 'px'));
    } else {
      setTimeout(function (e) {
        resizeIframe();
      }, 50);
    }
  }*/

  // Agrega la propiedad de autocompletado=off para todo formulario
  // Importante que todos los campos de texto, labels, y relacionados
  // se encuentren en etiquetas form
  $('form').attr('autocomplete', 'off');

  // Control de autocompletado para todos los input
  $(':input').on('focus', function () {
    $(this).attr('autocomplete', 'off');
  });

  // Para el funcionamiento de tooltips de bootstrap
  $(function () {
    $('[data-toggle="tooltip"]').tooltip()
  })

  //Para la animación de la flecha de irArriba en c-detalle-or
  $(window).scroll(function(){
    if( $(this).scrollTop() > 0 ){
        $('#IrArriba').slideDown(600);
        $('#toTop').slideDown(600);
    } else {
        $('#IrArriba').slideUp(600);
        $('#toTop').slideUp(600);
    }
  });



});