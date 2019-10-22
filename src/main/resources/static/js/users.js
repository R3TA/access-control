/**
 * 
 */
//$('.alert').alert('close');
$("#message-success").css({"display":"none"});
$("#message-error").css({"display":"none"})
$("#adduser").hide();
$("#add-user-form").css({"border":"1px solid #CACFD2"});
$(document).ready(function() {
	$("#add").click(function() { 
		$("#adduser").toggle();
	}); 
});

$(document).ready(function() {
	    $.ajax({
	        url: "http://localhost:9000/api/users"
	    }).then(function(data, status, jqxhr) {
	    	//alert(data.length);
	    	var tabla = "";
	    	for(var i = 0;i < data.length;i++){
				tabla = tabla + "<tr>"+
                                    "<td class='align-middle'>"+data[i]["id"]+"</td>"+  
                                    "<td class='align-middle'>"+data[i]["cardNumber"]+"</td>"+  
									"<td class='align-middle'>"+
										"<a href='/api/users/edit?id="+data[i]["id"]+"'><input type='image' src = '/../img/icons/boton-editar.png' alt='"+data[i]["id"]+"'></a>"+
										"&nbsp;&nbsp;&nbsp;&nbsp;"+
										"<input id='id' name='id' type='image' src='/../img/icons/boton-eliminar.png' alt='"+data[i]["id"]+"'></input>"+
									"</td>"+
                               "</tr>";
			}
			$("#grid-data").append(tabla);
	       console.log(jqxhr);
	    });
});

$(document).ready(function() {
		$("#add-user-form").submit(function (e) {
			//Prevent default submission of form
			e.preventDefault();
			var user = {};
			user["cardNumber"] = $("#cardNumber").val();
			var data = JSON.stringify(user);
		    
			try {/*
				var error = false;
				var nombre = $("#nombre").val();
				var fechanacimiento = $("#fechanacimiento").val();
				var activo = $("#activo").val();
				var colegio = $("#colegio").val();
				var asignatura = $("#asignatura").val();
				var profesor = $("#profesor").val();				
				var subs = "";
				
				if((nombre.length == 0 || !nombre.trim())){
					$("#nombre").attr({"title":"Completar campo obligatorio"});
					error = true;					
				}else{
					error = false;
				}*/
				alert("En process....");
				console.log(data);
					$.ajax ({
						type: "POST",
						url: "/api",
						data: data,
						contentType: "application/json",
						dataType: "json",
						encode: true,
						beforeSend: function (xhr) {
							console.log(xhr);
							$("#submit").attr({"disabled":"true"}).html("Enviando...");
						},        
						success: function (data, textStatus, jqXHR) {
							console.log(data);
							alert("done:"+textStatus+data.message);
							
							if (textStatus == "success") {
								$("#submit").removeAttr("disabled").html("Add");
								$("#container-message-success").css({"display":"block"});							
								$("#message-success").css({"display":"block"}).html("<strong>"+data.message+"</strong>" 
										+ "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>");
								//$("form[name='add-user-form'").reset();
								$("#add-user-form").val("");
								$("#cardNumber").val("");
								//var element = $('[name="add-user-form"]'); 
								//element.reset();
				                //hide the element 
				                //element.hide(); 
								//document.getElementById('add-user-form').reset();
							}
						},
						error:function (data, textStatus, jqXHR) {
							console.log("ERROR: "+ data.status);
							var user = JSON.stringify(data);
				    		var parser = JSON.parse(user);
							if(data.status == 500) {
								console.log("ERROR: "+ data.status);
								$("#container-message-error").css({"display":"block"});	
								$("#message-error").css({"display":"block"}).html("<strong>"+parser.responseJSON.message+"</strong>" 
										+ "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>");
								$("#submit").removeAttr("disabled").html("Add");								
							}
							
							if(data.status == 400) {
								console.log("ERROR: "+ data.status);
								//alert("Error: 400 "+ parser.responseJSON.errors[0]);
								$("#container-message-error").css({"display":"block"});	
								$("#message-error").css({"display":"block"}).html("<strong>"+parser.responseJSON.errors[0]+"</strong>" 
										+ "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>");
								$("#submit").removeAttr("disabled").html("Add");
							}
							console.log("DONE");							
						},
						 complete: function() {
							console.log("DONE");
						}
					});				
				//}
			}catch(e) {
				console.log("Error in POST method from add-user-form: "+e.message);   
			}
			return false;
		});	
});

function resetForm(){
	$("form[name='add-user-form'").reset();
}

