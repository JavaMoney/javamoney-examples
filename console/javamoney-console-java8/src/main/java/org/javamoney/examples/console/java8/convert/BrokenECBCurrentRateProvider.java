package org.javamoney.examples.console.java8.convert;

import org.javamoney.moneta.convert.ecb.ECBCurrentRateProvider;
import org.javamoney.moneta.spi.loader.LoadDataInformation;
import org.javamoney.moneta.spi.loader.LoadDataInformationBuilder;
import org.javamoney.moneta.spi.loader.LoaderService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BrokenECBCurrentRateProvider extends ECBCurrentRateProvider {
    private static final String DATA_ID = BrokenECBCurrentRateProvider.class.getSimpleName();

    public BrokenECBCurrentRateProvider() {
        super();
    }

    @Override
    public String getDataId() {
        return DATA_ID;
    }

    @Override
    protected LoadDataInformation getDefaultLoadData() {
        Map<String, String> props = new HashMap<>();
        props.put("period", "03:00");
        return (new LoadDataInformationBuilder())
                .withResourceId(this.getDataId())
                .withUpdatePolicy(LoaderService.UpdatePolicy.SCHEDULED)
                .withProperties(props)
                // The same BackupResource as in the ECBCurrentRateProvider class
                .withBackupResource(URI.create("classpath:/org/javamoney/moneta/convert/ecb/defaults/eurofxref-daily.xml"))
                // Broken url intentionally
                .withResourceLocations(URI.create("https://broken-url.xml"))
                .withStartRemote(true)
                .build();
    }
}