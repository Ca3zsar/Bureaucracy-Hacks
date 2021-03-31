@extends('layouts.main')

@section('content')
<div class="container">
    <div class="row justify-content-center align-items-center home-section">
        <div class="col-md-6 px-4">
            <h2 class="font-weight-bolder text-xl display-4"> Provides you with the best Solutions</h2>
            <p class="">
                Help with bureaucratic processes and complicated paperwork
            </p>
            <a href="" class="btn btn-accent-primary mt-4">Get stared</a>
        </div>
        <div class="col-md-6">
            <img src="{{ asset('/images/home/ilustration.svg') }}" alt="Path" class="home-section-image">
        </div>
    </div>
    <div class="row home-section">
        <div class="row col-12 justify-content-center align-items-center flex-column">
            <span class="section-category">Our Services</span>
            <h2 class="text-center">We Provide Best Quality Service</h2>
        </div>
        <div class="row col-md-12">

        </div>
    </div>
</div>
@endsection
