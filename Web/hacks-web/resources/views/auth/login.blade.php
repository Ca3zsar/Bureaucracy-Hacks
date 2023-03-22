@extends('layouts.auth')

@section('content')
<section class="auth-section">
    <div class="auth-image-container">
        <div class="auth-image"></div>
    </div>
    <div class="auth-content ">
        <form class="login-form simple-form" action="/login" method=>
            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-12 col-lg-7">
                    <input type="text" name="username" placeholder="username" class="input-form" />
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-12 col-lg-7">
                    <input type="password" name="password" placeholder="password" class="input-form" />
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center mb-4">
                <div class="col-2 col-md-1 p-0">
                    <input type="checkbox" name="remindme" class="input-form" />
                </div>
                <div class=" col-9 col-md-6 p-0">
                    <label class="d-flex align-items-center text-white m-0">Remind me</label>
                </div>

            </div>

            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-12 col-lg-7">
                    <button class="auth-button">Login</button>
                </div>
            </div>
            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item col-12 col-lg-7">
                    <a class="btn auth-button" href="/register">Create new account</a>
                </div>
            </div>
        </form>
        <div class="auth-link-container">
            <a class="auth-link" href="/forgot-password">Forgot password?</a>
        </div>
    </div>
</section>
@endsection
