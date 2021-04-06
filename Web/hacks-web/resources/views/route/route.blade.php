@extends('layouts.main')

@section('content')

    <div class="container grid">

        <div class="col-12 col-md-3  mt-10">
            <h3 class="document-text">Carte de identitate</h3>
        </div>

        <div class="dropdown">
            <div class="col-12 col-md-9 mt-5">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Documente necesare
                    <span class="caret"></span></button>
                <ul class="dropdown-menu checkbox-menu allow-focus">
                    <li >
                        <label>
                            <input type="checkbox"> Copie certificat nastere
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="checkbox"> Timbru
                        </label>
                    </li>
                    <li >
                        <label>
                            <input type="checkbox"> Cartea de identitate curenta
                        </label>
                    </li>
                </ul>
            </div>
            <div class="col-12 col-md-9 mt-3">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Conditii de
                    eliberare
                    <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li>Programare valida</li>
                    <li>Carte de identitate</li>
                    <li>Certificat de nastere</li>
                </ul>
            </div>

            <div class="col-12 col-md-9 mt-3">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Institutii unde se
                    desfasoara
                    <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li>Politia locala</li>
                    <li>Primarie</li>
                </ul>
            </div>

        </div>

        <div class="col-12 col-md-9 mt-3">
            <button type="button" class="btn btn-route">Creeaza ruta</button>
        </div>


    </div>

    <div class="container">
        <div class="row">

            <div class="flex align-items-center col-12 col-md-12 mt-3">
                <h2 class="text-center text-color-blue">Traseul meu</h2>
            </div>

            <div class="col-12 col-md-12 mt-3">
                <div id="map-container-google-1" class="z-depth-1-half map-container">
                    <iframe src="https://maps.google.com/maps?q=Iasi&t=&z=13&ie=UTF8&iwloc=&output=embed"
                            frameborder="0"></iframe>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid container-fluid-background mt-10">
        <div class="p-5">
            <div class="d-flex flex-row justify-content-center">
                <h2>Feedback-ul tau conteaza !</h2>
                <a href="#" class="ml-3 btn button-accent-secondary">Ofera feedback</a>
            </div>
        </div>


    </div>



    <div class="container">
        <div class="row">

            <div class="flex align-items-center col-12 col-md-12 mt-5">
                <h2 class="text-center text-color-blue"> Timpul mediu de asteptare</h2>
            </div>
        </div>

        <div class="row position-relative"><canvas id="bar-chart" width="800" height="450"></canvas></div>


    </div>


@endsection

@section('scripts')
    <script>
        var chart = new Chart(document.getElementById("bar-chart").getContext('2d'), {
            type: 'bar',
            data: {
                labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
                datasets: [
                    {
                        label: "Population (millions)",
                        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
                        data: [2478,5267,734,784,433]
                    }
                ]
            },
            options: {
                legend: { display: false },
                title: {
                    display: true,
                    text: 'Predicted world population (millions) in 2050'
                }
            }
        });
        console.log(chart);
    </script>
@endsection
