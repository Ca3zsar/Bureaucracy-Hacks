@extends('layouts.auth')

@section('content')
<section class="auth-section">
    <div class="auth-image-container">
        <div class="auth-image"></div>
    </div>
    <div class="auth-content ">
        <form class="login-form simple-form" action="/login" method=>
            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-md-12 col-12">
                    <input type="text" name="username" placeholder="username" class="input-form" />
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-md-12 col-12">
                    <input type="password" name="password" placeholder="password" class="input-form" />
                </div>
            </div>
            <div class="auth-item mb-4">
                <button class="auth-button">Login</button>
            </div>
            <div class="auth-item">
                <a class="btn auth-button" href="/register">Create new account</a>
            </div>
        </form>
        <div class="auth-link-container">
            <a class="auth-link" href="/forgot-password">Forgot password?</a>
        </div>
    </div>
</section>
@endsection