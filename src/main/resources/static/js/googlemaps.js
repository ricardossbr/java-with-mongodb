autoComplete = () => {
	const input = document.getElementById('endereco');
	autocomplete = new google.maps.places.Autocomplete(input);
}

initMap = () => {
	const brasil = {
		lat : -14.239183,
		lng : -51.913726
	};
	const map = new google.maps.Map(document.getElementById('map'), {
		center : brasil,
		scrollwheel : false,
		zoom : 4
	});
	for (index = 0; index < alunos.length; ++index) {
	    const latitude = alunos[index].contato.coordinates[0];
	    const longitude = alunos[index].contato.coordinates[1];
	    const coordenadas = {
	    		lat : latitude,
	    		lng : longitude
	    	};
	    const marker = new google.maps.Marker({
			position : coordenadas,
			label: alunos[index].nome
		});
	    marker.setMap(map);
	}
}