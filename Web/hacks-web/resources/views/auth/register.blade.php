@extends('layouts.app')

@section('content')

<section class="auth-section">
    <div class="auth-image-container">
        <div class="auth-image"></div>
    </div>
    <div class="auth-content ">
        <form>
            <div class="auth-item mb-4">
                <input type="text" name="username" placeholder="username" class="input-form" />
            </div>

            <div class="auth-item mb-4">
                <input type="password" name="password" placeholder="password" class="input-form" />
            </div>
            <div class="auth-item">
                <button class="auth-button">login</button>
            </div>
        </form>
        <div class="auth-item">
            <button class="auth-button">register</button>
        </div>
        <div class="auth-link-container">
            <a class="auth-link">Forgot password?</a>
        </div>
        <div class="auth-link-container">
            <a class="auth-link">Dont have an account?</a>
        </div>
    </div>
</section>
@endsection