package com.mx.profuturo.android.pensionat.presentation.formulario;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class SingleFragmentPagerAdapterTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private Fragment fragment;
    private SingleFragmentPagerAdapter adapter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        List<Fragment> fragments;
        fragments = new ArrayList<>();
        fragment = Mockito.mock(Fragment.class);
        fragments.add(fragment);
        FragmentManager fragmentManager = Mockito.mock(FragmentManager.class);
        adapter = new SingleFragmentPagerAdapter(fragmentManager, fragments);
    }

    @Test
    public void testCount() {
        Assert.assertEquals(MENSAJE, 1, adapter.getCount());
    }

    @Test
    public void testGetItemPositio() {
        Assert.assertEquals(MENSAJE, POSITION_NONE, adapter.getItemPosition(0));
    }

    @Test
    public void testGetItem() {
        Assert.assertEquals(MENSAJE, fragment, adapter.getItem(0));
    }
}
