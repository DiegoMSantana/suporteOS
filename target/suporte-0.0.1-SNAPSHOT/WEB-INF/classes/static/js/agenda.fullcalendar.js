var Suporte= Suporte || {};

Suporte.Calendario = (function() {

	function Calendario() {
		this.calendario = $('#calendar');
	}

	Calendario.prototype.iniciar = function() {
		
		init_events($('#external-events div.external-event'));
		$('#modal-inserir-produto-btn').on('click', onSalvaNovoEvento.bind(this));
		$('#modal-atualizar-evento-btn').on('click', onAtualizarEvento.bind(this));
		$('#modal-deletar-evento-btn').on('click', onDeletarEvento.bind(this));
		
		
		carregaCalendario();
		    
	}
	
	function carregaCalendario(){
		
		 $('#calendar').fullCalendar({
				
			 monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
			 dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
			 dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],

		      header    : {
		        left  : 'prev,next today',
		        center: 'title',
		        right : 'month,agendaWeek,agendaDay'
		      },
		      buttonText: {
		        today: 'Hoje',
		        month: 'Mês',
		        week : 'Semana',
		        day  : 'dia'
		      },
		      weekNumbers: true,
		      eventLimit: true,
		      selectable: true,
			  selectHelper: true,
			  editable  : true,
		      droppable : true,
		      timeFormat: 'HH:mm',
		      events: {
				    	  url: "agenda/buscar",
				    	  allDay: true
				      },
				     
			  eventClick: alteraEvento.bind(this),
			  select: selecionaEvento.bind(this),		  			
		      eventDrop: eventoDrop.bind(this),
		      eventResize : eventoResizable.bind(this),
		      drop : function(date, allDay){
			    	
			        // retrieve the dropped element's stored Event Object
			        var originalEventObject = $(this).data('eventObject')
			        
			        // we need to copy it, so that multiple events don't have a
					// reference to the same object
			        var copiedEventObject = $.extend({}, originalEventObject)
			                
			        // assign it the date that was reported
			        copiedEventObject.start           = date
			        copiedEventObject.allDay          = allDay
			        copiedEventObject.backgroundColor = $(this).css('background-color')
			        copiedEventObject.borderColor     = $(this).css('border-color')

			        // render the event on the calendar
			        // the last `true` argument determines if the event "sticks"
					// (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
			        $('#calendar').fullCalendar('renderEvent', copiedEventObject, true)


			        // is the "remove after drop" checkbox checked?
			        if ($('#drop-remove').is(':checked')) {
			          // if so, remove the element from the "Draggable Events"
						// list
			          $(this).remove()
			        }
		    	  
		      }
		    })

		    /* ADDING EVENTS */
		    var currColor = '#3c8dbc' // Red by default
		    // Color chooser button
		    var colorChooser = $('#color-chooser-btn')
		    $('#color-chooser > li > a').click(function (e) {
		      e.preventDefault()
		      // Save color
		      currColor = $(this).css('color')
		      // Add color effect to button
		      $('#add-new-event').css({ 'background-color': currColor, 'border-color': currColor })
		    })
			
			
		    $('#add-new-event').click(function (e) {
		      e.preventDefault()
		      // Get value and make sure it is not null
			  
		      var val = $('#new-event').val()
		      if (val.length == 0) {
		        return
		      }

		      // Create events
		      var event = $('<div />')
		      event.css({
		        'background-color': currColor,
		        'border-color'    : currColor,
		        'color'           : '#fff'
		      }).addClass('external-event')
		      event.html(val)
		      $('#external-events').prepend(event)

		      // Add draggable funtionality
		      init_events(event)

		      // Remove event from text input
		      $('#new-event').val('')
		    })		  
	}
	
	 function alteraEvento(event){
		 
		limpaCampos();
		 
		$('#idEvento').val(event.id);
		$('#eventoUpdate').val(event.title);
		$('#dataHoraInicioUpdate').val(event.start.format('DD/MM/YYYY HH:mm'));
		$('#dataHoraFimUpdate').val(event.end.format('DD/MM/YYYY HH:mm'));
		$('#colorUpdate').val(event.color)
		$('#observacoesUpdate').val(event.observacoes);
		$('#modal-agenda-atualizar').modal({
			 backdrop: 'static',
             keyboard: false, 
             show: true
		});
	 }
	 
	 function selecionaEvento(start, end){
		 
		limpaCampos();
		  
		$('#dataHoraInicio').val(start.format('DD/MM/YYYY HH:mm'));
		$('#dataHoraFim').val(end.format('DD/MM/YYYY HH:mm'));
		$('#modal-agenda').modal({
			 backdrop: 'static',
             keyboard: false, 
             show: true
		});
		 
	 }
	 
	 function eventoDrop(event){
		 console.log("drop event");
   	  	 console.log(event.title);
	 }
	 
	 function onSalvaNovoEvento(event){
		 
		 event.preventDefault();					  
		  var dataInicio =  moment($('#dataHoraInicio').val(), 'DD/MM/YYYY HH:mm', true).format('YYYY-MM-DD HH:mm');
		  var dataFim = moment($('#dataHoraFim').val(), 'DD/MM/YYYY HH:mm', true).format('YYYY-MM-DD HH:mm');

		  var resposta = $.ajax({
				url : 'agenda/salvar',
				method : 'POST',
				contentType : 'application/json',
				beforeSend : function() {
					console.log("Carregando...");
				},
				data : JSON.stringify({
					title: $('#evento').val(),
					dataHoraInicio: dataInicio,
					dataHoraFim: dataFim,
					color: $('#color').val(),
					observacoes: $('#observacoes').val()
				})
			});
		  resposta.done(function(resposta){
			 $('#modal-agenda').modal("hide");
			 $('#calendar').fullCalendar('refetchEvents');
			 toastee.call(this, resposta);
		  }),
		  resposta.fail(function(){
			  console.log("Não foi possivel fazer o agendamento");
		  });
		 
	 }
	 
	 function onAtualizarEvento(event){
		 
		 event.preventDefault();
		 var idSelecionado = $('#idEvento').val();
		 var dataInicio =  moment($('#dataHoraInicioUpdate').val(), 'DD/MM/YYYY HH:mm', true).format('YYYY-MM-DD HH:mm');
		 var dataFim = moment($('#dataHoraFimUpdate').val(), 'DD/MM/YYYY HH:mm', true).format('YYYY-MM-DD HH:mm');
			
			var resposta = $.ajax({
				url : "agenda/atualizar/" + idSelecionado,
				method : 'PUT',
				contentType : 'application/json',
				data : JSON.stringify({
					title: $('#eventoUpdate').val(),
					dataHoraInicio: dataInicio,
					dataHoraFim: dataFim,
					color: $('#colorUpdate').val(),
					observacoes: $('#observacoesUpdate').val()
				})
			});
			
			resposta.done(function(resposta){
				 $('#modal-agenda-atualizar').modal("hide");
				 $('#calendar').fullCalendar('refetchEvents');
				 toastee.call(this, resposta);	 
			});
			resposta.fail(function(){
				console.log("Se fodeu!!!kkk");
			});
	 }
	 
	 function onDeletarEvento(event){
		 event.preventDefault();
		 var idSelecionado = $('#idEvento').val();
		 
		 var resposta = $.ajax({
				url : 'agenda/deletar/' +  idSelecionado,
				method : 'DELETE'
			});	
		 
		 resposta.done(function(resposta){
			 $('#modal-agenda-atualizar').modal("hide");
			 $('#calendar').fullCalendar('refetchEvents');
			 toastee.call(this, resposta);
		});
		 
		resposta.fail(function(resposta){
			console.log(resposta);
		});
			
		
	 }
	 
	 function eventoResizable(event){
		 console.log(event.title); 
	 }
	 	 
	 function limpaCampos(){
		 
		$('#evento').val('');
		$('#dataHoraInicio').val('');
		$('#dataHoraFim').val('');
		$('#observacoes').val('');
	 }
	 
	 function toastee(mensagem){	
		 $.toast({
			heading: 'Sucesso',
			text: mensagem,
			showHideTransition: 'slide',
			icon: 'success',
			position: 'bottom-right',
		})
	 }
	 
	 function init_events(ele) {
	      ele.each(function () {

	        // create an Event Object
			// (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
	        // it doesn't need to have a start or end
	        var eventObject = {
	          title: $.trim($(this).text()) // use the element's text as the
											// event title
	        }

	        // store the Event Object in the DOM element so we can get to it
			// later
	        $(this).data('eventObject', eventObject)

	        // make the event draggable using jQuery UI
	        $(this).draggable({
	          zIndex        : 1070,
	          revert        : true, // will cause the event to go back to its
	          revertDuration: 0  // original position after the drag
	        })

	      })
	    }


	return Calendario;

}());

$(function() {
	var mascaraCpfCnpj = new Suporte.Calendario();
	mascaraCpfCnpj.iniciar();
});