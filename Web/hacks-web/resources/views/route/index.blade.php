@extends('layouts.main')
@section('content')

    <div class="container mt-10">

        <div class="row">
        <div class="col-12 col-md-9">

            <div class="col-12 col-md-5 mt-10">
                <h3 class="document-text">Carte de identitate</h3>
            </div>

            <form>
                <div class="dropdown">
                    <div class="col-12 col-md-9 mt-5">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Documente
                            necesare
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu checkbox-menu allow-focus p-2">
                            <li class="border-dark">
                                <label>
                                    <input type="checkbox"> Copie certificat nastere
                                </label>
                            </li>
                            <li>
                                <label>
                                    <input type="checkbox"> Timbru
                                </label>
                            </li>
                            <li>
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
                        <ul class="dropdown-menu p-2">
                            <li>Programare valida</li>
                            <li>Carte de identitate</li>
                            <li>Certificat de nastere</li>
                        </ul>
                    </div>

                    <div class="col-12 col-md-9 mt-3 ">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Institutii
                            unde
                            se
                            desfasoara
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu checkbox-menu allow-focus p-2">
                            <li>
                                <label>
                                    <input type="checkbox"> Politia locala
                                </label>
                            </li>
                            <li>
                                <label>
                                    <input type="checkbox"> Primaria
                                </label>
                            </li>

                        </ul>
                    </div>

                </div>

                <div class="col-12 col-md-9 mt-3">
                    <button type="button" class="btn btn-route">Creeaza ruta</button>
                </div>
            </form>
        </div>

        <div class="col-12 col-md-3 sm-hidden d-flex align-items-center justify-content-center">
            <img style="width: 80%" src="{{asset('/images/svg/document.svg')}}" />
        </div>

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
            <div class="align-feedback">
                <h2>Feedback-ul tau conteaza !</h2>
                <a href="#" class="btn button-accent-secondary">Ofera feedback</a>
            </div>
        </div>


    </div>


    <div class="container">
        <div class="row">

            <div class="flex align-items-center col-12 col-md-12 mt-5">
                <h2 class="text-center text-color-blue mb-3"> Timpul mediu de asteptare</h2>
            </div>
        </div>

        <statistics></statistics>

    </div>


@endsection
