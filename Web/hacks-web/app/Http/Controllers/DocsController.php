<?php

namespace App\Http\Controllers;

class DocsController extends Controller
{

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Contracts\Support\Renderable
     */
    public function index()
    {
        return view('documents.index');
    }
}
