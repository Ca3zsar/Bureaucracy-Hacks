@extends('layouts.main')

@section('content')
<div class="container">
    <section class="row docs-section flex justify-content-center align-items-center">
        <div class="col-12">
            <input type="text" name="search-doc" placeholder="Ce documente te intereseaza?"
                class="form-control search-docs">
        </div>
    </section>

    <section class="row docs-section">
        <div class="text-center">
            <h3 class="docs-section-title">Carte de identitate</h3>
        </div>
        <div class="col-12">
            <div class="row">
                <div class="col-8">
                    <div class="dropdown show">
                        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> Documente necesare

                        </a>

                        <ol class="dropdown-menu docs-dropdown col-12" aria-labelledby="dropdownMenuLink">

                            <li class="dropdown-item" >- Cerere pentru eliberarea actului de identitate</li>
                            <li class="dropdown-item" >- Certificatul de nastere(fotocopie si original)</li>

                            <li class="dropdown-item" >- Documentul cu care solicitantul face dovada adresei de
                                domiciliu</li>
                            <li class="dropdown-item" >- Daca gazduitorul nu se poate prezenta la serviciul
                                public comunitar de evidenta a persoanelor, declaratia de primire in spatiu poate fi
                                consemnata la notarul public, la misiunea diplomatica sau oficiul consular al Romaniei
                                din strainatare sauu in prezenta politistului de la postul de politie pentru mediul
                                rural.</li>

                        </ol>
                    </div>
                </div>
                <div class="col-4">
                    <img src="/images/svg/document.svg">
                </div>
            </div>
        </div>
    </section>
</div>
<section class="container-fluid bg-primary-accent mb-5">
    <div class="row p-5 align-items-center justify-content-center ">
        <div class="col-12 d-flex justify-content-center">
            <h3 class="text-white">Feedback-ul tau conteaza</h3>
            <a href="/contact" class="btn ml-3 button-accent-secondary">Ofera feedback</a>
        </div>
    </div>
</section>
<section class="container">
    <div class="row">
        <div class="text-center">
            <h3 class="docs-section-title">Pro Tips</h3>
        </div>
    </div>
    <div class="row mt-5">
        <div class="col-12">
            <ol class="panel ">

                <li class="panel-item"><img src="/images/svg/alert.svg"> Cerere pentru eliberarea actului de identitate</li>


            </ol>
        </div>
    </div>
</section>

@endsection